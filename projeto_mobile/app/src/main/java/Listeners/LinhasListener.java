package Listeners;

import java.util.ArrayList;

import Models.LinhaFatura;

public interface LinhasListener {
    void onRefreshLinhasFatura(ArrayList<LinhaFatura> linhas);
}
