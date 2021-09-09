package com.lxh.coolweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lxh.coolweather.db.entity.City;
import com.lxh.coolweather.db.entity.County;
import com.lxh.coolweather.db.entity.Province;
import com.lxh.coolweather.db.util.CityDaoUtil;
import com.lxh.coolweather.db.util.CountyDaoUtil;
import com.lxh.coolweather.db.util.ProvinceDaoUtil;
import com.lxh.coolweather.util.HttpUtil;
import com.lxh.coolweather.util.LogUtil;
import com.lxh.coolweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView tv_title;
    private Button btn_back;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;

    private ProvinceDaoUtil provinceDaoUtil = new ProvinceDaoUtil();
    private CityDaoUtil cityDaoUtil = new CityDaoUtil();
    private CountyDaoUtil countyDaoUtil = new CountyDaoUtil();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    //查询城市
                    queryCities();

                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    //查询县
                    queryCounties();
                }
            }
        });

        btn_back.setOnClickListener(v -> {
            if (currentLevel == LEVEL_COUNTY) {
                //查询城市
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                //查询省份
                queryProvinces();
            }
        });
        //查询省份
        queryProvinces();
    }

    //查询全国所有省份，优先从数据库查询，没有则去服务器查询
    private void queryProvinces() {
        tv_title.setText("中国");
        btn_back.setVisibility(View.GONE);
        //从数据库查询
        String sql = "SELECT * FROM PROVINCE";
        provinceList = provinceDaoUtil.findAllProvinces(sql,null);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            //从服务器查询
            queryFromServer(address,"province");

        }
    }

    //查询选中省份的城市，优先从数据库查询，没有则去服务器查询
    private void queryCities() {
        tv_title.setText(selectedProvince.getProvinceName());
        btn_back.setVisibility(View.GONE);
        int provinceCode = selectedProvince.getProvinceCode();
        //从数据库查询
        String sql = "SELECT * FROM CITY WHERE PROVINCE_ID = ?";
        cityList = cityDaoUtil.findAllCities(sql,new String[]{String.valueOf(provinceCode)});
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            String address = "http://guolin.tech/api/china/" + provinceCode;
            //从服务器查询
            queryFromServer(address,"city");

        }
    }

    //查询选中城市的县，优先从数据库查询，没有则去服务器查询
    private void queryCounties() {
        tv_title.setText(selectedCity.getCityName());
        btn_back.setVisibility(View.GONE);
        int provinceCode = selectedProvince.getProvinceCode();
        int cityCode = selectedCity.getCityCode();
        //从数据库查询
        String sql = "SELECT * FROM COUNTY WHERE CITY_ID = ?";
        countyList = countyDaoUtil.findAllCounties(sql,new String[]{String.valueOf(cityCode)});
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            //从服务器查询
            queryFromServer(address,"county");

        }
    }

    //根据传入的地址和类型从数据库查询省市县数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseData);
                    LogUtil.e("result2",String.valueOf(result));
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseData, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseData, selectedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    //关闭进度对话框
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
