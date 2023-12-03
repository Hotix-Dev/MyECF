package com.e2p.myecf.helpers;

import com.e2p.myecf.models.Exercice;
import com.e2p.myecf.models.StatementType;

import java.util.ArrayList;

public class Stub {

    public static ArrayList<Exercice> getExercicesList() {
        ArrayList<Exercice> _Ret = new ArrayList<Exercice>();
        try {

            _Ret.add(new Exercice(1, "2020", "2020"));
            _Ret.add(new Exercice(2, "2021", "2021"));
            _Ret.add(new Exercice(3, "2022", "2022"));
            _Ret.add(new Exercice(4, "2023", "2023"));
            _Ret.add(new Exercice(5, "2024", "2024"));
            _Ret.add(new Exercice(6, "2025", "2025"));
            _Ret.add(new Exercice(7, "2026", "2026"));
            return _Ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Exercice>();
        }
    }

    public static ArrayList<StatementType> getStatementTypesList() {
        ArrayList<StatementType> _Ret = new ArrayList<StatementType>();
        try {

            _Ret.add(new StatementType(1, "Planning Fiscaux", ""));
            _Ret.add(new StatementType(2, "Planning Sociaux", ""));
            _Ret.add(new StatementType(3, "Autres Plannings", ""));
            return _Ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<StatementType>();
        }
    }

}
