package app.client;


import app.service.ContextService;
import bank.wsdl.*;
import bank.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class BankClient extends WebServiceGatewaySupport {

    private static final String ENDPOINT = "http://localhost:8080/ws";

    @Autowired
    private ContextService contextService;

    public List<Account> getAccountsRequest(String id) {
        GetAccountsRequest request = new GetAccountsRequest();
        request.setClientId(id);

        GetAccountsResponse response = (GetAccountsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(ENDPOINT));
        return response.getAccounts();
    }

    public Account getAccounr(String nrb){
        GetAccountRequest request = new GetAccountRequest();
        request.setAccountNbr(nrb);

        GetAccountResponse response = (GetAccountResponse)getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(ENDPOINT));
        return response.getAccount();
    }

    public List<History> getHistory(String nrb){
        GetTransactionsRequest request = new GetTransactionsRequest();
        request.setNrb(nrb);
        GetTransactionsResponse response = (GetTransactionsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(ENDPOINT));
        return response.getTransactions();
    }

    public MakeTransferResponse makeTransfer(String destinationAccount, String destinationName, String title, int amount){
        MakeTransferRequest request = new MakeTransferRequest();
        request.setDestinationAccount(destinationAccount);
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setDestinationName(destinationName);
        transfer.setSourceAccount(contextService.getNbr());
        transfer.setSourceName(contextService.getClientName());
        transfer.setTitle(title);
        transfer.setTitle(title);
        request.setTransfer(transfer);

        return (MakeTransferResponse)getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(ENDPOINT));
    }

    public AddPaymentResponse addPayment(double amount, String title){
        AddPaymentRequest request = new AddPaymentRequest();
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setNrb(contextService.getNbr());
        payment.setTitle(title);
        request.setPayment(payment);

        return (AddPaymentResponse)getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(ENDPOINT));
    }

    public AddWithdrawalResponse addWithdrawal(double amount, String title){
        AddWithdrawalRequest request = new AddWithdrawalRequest();
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setNrb(contextService.getNbr());
        payment.setTitle(title);
        request.setPayment(payment);

        return (AddWithdrawalResponse)getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(ENDPOINT));
    }

    public Client getClient(String clientId){
        GetClientRequest request = new GetClientRequest();
        request.setClientId(clientId);

        GetClientResponse response = (GetClientResponse) getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(ENDPOINT));
        return response.getClient();
    }

    public boolean loginToSystem(String clientId, String password){
        LoginRequest request = new LoginRequest();
        Authoryzation authoryzation = new Authoryzation();
        authoryzation.setClientId(clientId);
        authoryzation.setPassword(password);
        request.setAuthoryzation(authoryzation);

        LoginResponse loginResponse = (LoginResponse) getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(ENDPOINT));
        String token = loginResponse.getToken();

        if(token != null){
            contextService.setToken(token);
            contextService.setClientId(clientId);

            Client client = getClient(clientId);
            contextService.setClientName(client.getName()+ " "+client.getSurname());

            return true;
        }else {
            return false;
        }

    }
}
