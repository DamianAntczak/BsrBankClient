package app.client;

import bank.wsdl.GetAccountRequest;
import bank.wsdl.GetAccountsRequest;
import bank.wsdl.GetAccountsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class BankClient extends WebServiceGatewaySupport{

    public GetAccountsResponse getAccountsRequest(String id){
        GetAccountsRequest request = new GetAccountsRequest();
        request.setClientId(id);

        GetAccountsResponse response = (GetAccountsResponse)getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback("http://localhost:8080/ws"));
        return response;
    }
}
