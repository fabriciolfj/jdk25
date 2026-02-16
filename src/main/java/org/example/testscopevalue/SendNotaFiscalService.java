package org.example.testscopevalue;

public class SendNotaFiscalService implements  ProcessByService {

    @Override
    public  void execute(final Person person) {
        IO.println("send nf to person " + person + " , correlation id " + ScopeValueCorrelationUtil.getValue());
    }
}
