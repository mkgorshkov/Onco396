package com.mgorshkov.hig.model;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class DiagnosisModel {
    private String description;
    private String category;

    public DiagnosisModel(){
        description = "";
        category = "";
    }

    public DiagnosisModel(String d, String c){
        description = d;
        category = c;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
