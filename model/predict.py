from pickle import load, dump
from datetime import timedelta
from flask import Flask, request, jsonify
from flask_cors import CORS

import pandas as pd
import numpy as np

# import json
from statsmodels.tsa.seasonal import STL
from sklearn.preprocessing import MinMaxScaler
from xgboost import XGBRegressor

app = Flask(__name__)
CORS(app)

# model, scaler 불러오기
model=load(open('saved_model.pkl', 'rb'))
load_minmax_scaler = load(open('minmax_scaler.pkl', 'rb'))


@app.route('/aws/update', methods=['POST'])
def receive_and_process():
    try:
        jsondata = request.json
        water1 = jsondata['water1']
        water2 = jsondata['water2']
        aws = jsondata['rain']
    
        new_row=[water1, water2, aws]
        predictions_list = final_model(new_row)
    
        return jsonify({'result': [float(x) for x in predictions_list]}), 200
    except Exception as e:
        print(e)
        return jsonify({'error': str(e)}), 500


def makevariable(data):
    date = pd.to_datetime(data.iloc[:,0])
    data['hour'] = date.dt.hour
    data['day'] = date.dt.weekday
    data['month'] = date.dt.month
    data['year'] = date.dt.year
    data['sin_time'] = np.sin(2*np.pi*data.hour/24)
    data['cos_time'] = np.cos(2*np.pi*data.hour/24)
    data['resid'] = 0
    stl = STL(data['남도대교_수위'], period=24)
    res = stl.fit()
    data['resid'] = res.resid.values
    for i in range(37):
        data["{}_{}hours_prev".format('Value', i )] = data['남도대교_수위'].shift(i)
    data['value_after1']=data['남도대교_수위'].shift(-1)
    data['value_after2']=data['남도대교_수위'].shift(-2)
    data['강수량_차분']=data['강수량(mm)']-data['강수량(mm)'].shift(1)
    data['남도대교수위_차분']=data['남도대교_수위']-data['남도대교_수위'].shift(1)
    data['남도대교수위_2차차분']=data['남도대교_수위']-data['남도대교_수위'].shift(2)
    return data


def final_model(new_row):
    # new_row: 새로 넣을 값 리스트
    data=pd.read_csv("data.csv")
    start_datetime=pd.to_datetime(data.iloc[-1,0])
    pred_list=load(open('pred_list.pkl', 'rb'))
    if (start_datetime.dayofweek==6 and start_datetime.hour==23): #데이터의 마지막 행의 요일이 일요일일때: 업데이트 시간
        df_=data.copy()
        df=makevariable(df_)
        target = df['남도대교_수위'].shift(-3) #3시간 뒤의 수위 예측
        target=target[36:-3]
        df=df[36:-3]
        df=df.drop(columns=['일시'])
        scaler = MinMaxScaler()
        t_df = scaler.fit_transform(df)
        model = XGBRegressor(random_state=43, max_depth=7, tree_method='hist')
        model.fit(t_df, target)
        dump(model, open('saved_model.pkl', 'wb'))
        dump(scaler, open('minmax_scaler.pkl', 'wb'))
    model=load(open('saved_model.pkl', 'rb'))
    scaler = load(open('minmax_scaler.pkl', 'rb'))
    end_datetime = start_datetime + timedelta(hours=1)
    new_row.insert(0, end_datetime)
    new_row = pd.DataFrame([new_row], columns=data.columns)
    data = pd.concat([data, new_row], ignore_index=True)
    data.to_csv("data.csv", index=False)
    df=makevariable(data)
    df['value_after1']=pred_list[0]
    df['value_after2']=pred_list[1]
    inp=df.iloc[-1,1:]
    input_scaled = scaler.transform(inp.values.reshape(1, -1))
    result = pred_list
    result.append(model.predict(input_scaled)[0])
    pred_list = pred_list[1:]
    dump(pred_list, open('pred_list.pkl', 'wb'))

    return result


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
