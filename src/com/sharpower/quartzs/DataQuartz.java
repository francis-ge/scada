package com.sharpower.quartzs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.sharpower.beckhoff.FunDataReadWriteBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.entity.PlcType;
import com.sharpower.service.PlcDataReader;
import com.sharpower.service.PlcTypeService;
import com.sharpower.service.RecodeService;

import de.beckhoff.jni.tcads.AdsCallDllFunction;

public class DataQuartz {
	private PlcTypeService plcTypeService;
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private RecodeService recodeService;
	private List<PlcType> plcTypes = new ArrayList<>();
	private Map<Integer, Boolean> readDataTheadStaMap = new ConcurrentHashMap<>();
	private Map<Integer,Map<String, Object>> dataMap = new ConcurrentHashMap<>();
	private Map<String, Object> params = new HashMap<>();
	private List<Fun> funs = new ArrayList<>();
	
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
	}
	public void setFunDataReadWriteBeckhoffService(FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService) {
		this.funDataReadWriteBeckhoffService = funDataReadWriteBeckhoffService;
	}
	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Map<Integer, Map<String, Object>> getDataMap() {
		return dataMap;
	}

	private void init(){
		if (funs.isEmpty()){
			plcTypes = plcTypeService.findEntityByHQL("FROM PlcType p LEFT JOIN FETCH p.funs");
			
			for ( PlcType plcType : plcTypes ) {
				funs.addAll(plcType.getFuns());
			}
		
			for (Fun fun : funs) {
				readDataTheadStaMap.put(fun.getId(), false);
				
				if (fun.getPlcType().getPlcCommType().getName().equals("beckhoff")) {					
					AdsCallDllFunction.adsPortOpen();
				}
			}
			
		}
	}
	
	public void readData(){
		synchronized (this) {
			init();
			
			for (Fun fun : funs) {
				PlcDataReader plcDataReader;
				
				if (fun.getPlcType().getPlcCommType().getName().equals("beckhoff")) {
					plcDataReader = funDataReadWriteBeckhoffService;
				}else {
					plcDataReader = funDataReadWriteBeckhoffService;
				}
				
				//检查上轮线程状态，如已完成则创建新线程，未完成则不创建		
				Boolean threadSta = readDataTheadStaMap.get(fun.getId());
	
				if (threadSta!=true) {
					readDataTheadStaMap.put(fun.getId(), true);
					
					FunDataThread funDataThread = new FunDataThread();
					funDataThread.setPlcData(plcDataReader);
					funDataThread.setFun(fun);
					funDataThread.setParams(params);
					funDataThread.setDataMap(dataMap);
					funDataThread.setReadDataTheadStaMap(readDataTheadStaMap);
					
					Thread thread = new Thread(funDataThread);
					thread.start();
	
				}
					
			}
		}
	}
	
	public void saveData(){
		Date now = new Date();
		
		for (Entry<Integer,Map<String, Object>> entry : dataMap.entrySet()) {
			Map<String, Object> dataTemp = new HashMap<>();

			dataTemp.putAll(entry.getValue());
			
			dataTemp.put( "id", null );
			dataTemp.put( "dateTime", now );
			
			recodeService.save( dataTemp );
		}
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		for (Fun fun : funs) {
			AdsCallDllFunction.adsPortClose();
		}
	}
		
}
