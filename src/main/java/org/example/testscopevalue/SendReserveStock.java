package org.example.testscopevalue;

public class SendReserveStock implements ProcessByService {

    @Override
    public void execute(final Person person) {
        IO.println("reseve stock to person " + person + " , correlation id " + ScopeValueCorrelationUtil.getValue());
    }
}
