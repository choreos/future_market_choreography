package br.usp.ime.futuremarket.orchestration;

import br.usp.ime.futuremarket.AbstractWSInfo;

public class WSInfo extends AbstractWSInfo {
    @Override
    protected String getArchType() {
        return "orchestration";
    }
}