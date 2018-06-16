package com.app.books.booksapp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

public class WebService {

	
	private static final String NAMESPACE="http://tempuri.org/";
	
	private static final String URL="http://booksapp.uk/book_details.asmx";

	public static class webService_GetDataFromPortal{
		
		private static final String SOAP_ACTION="http://tempuri.org/GetData";
		private static final String METHOD_NAME="GetData";
		public static SoapObject soapObject;
		
		public static SoapObject callWebservice(Context con,String val){
			
			SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.implicitTypes = true;
			envelope.dotNet=true;
			
			SoapObject requestSoap=new SoapObject(NAMESPACE, METHOD_NAME);
			
			requestSoap.addProperty("ISBN", val);

			envelope.setOutputSoapObject(requestSoap);
			
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,60000);
			try{
				androidHttpTransport.call(SOAP_ACTION, envelope);
				if (androidHttpTransport.debug){
					Log.d("ws", androidHttpTransport.requestDump);
				}
				return deserializeSoap((SoapObject)envelope.bodyIn);
			}catch(Exception excp){
				excp.printStackTrace();
			}
			return null;
			//SaveStorageDetailsResponse{SaveStorageDetailsResult=anyType{respCount=0; isSuccess=false; errorMsg= is not a valid value for Int32.,InnerEx:System.IndexOutOfRangeException: Index was outside the bounds of the array.
		}
		//SaveStorageDetailsResponse{SaveStorageDetailsResult=anyType{respCount=1; isSuccess=true; errorMsg=All Records Saved Successfully; }; }
		public static SoapObject deserializeSoap(SoapObject paramObject){
			if(paramObject.hasProperty("GetDataResult")){
				soapObject = (SoapObject) paramObject.getProperty("GetDataResult");
					/*String count =  soapObject.getProperty("respCount").toString();
					String isSucess =  soapObject.getProperty("isSuccess").toString();
					String msg =  soapObject.getProperty("errorMsg").toString();*/
			}
			return soapObject;
		}
	}
}