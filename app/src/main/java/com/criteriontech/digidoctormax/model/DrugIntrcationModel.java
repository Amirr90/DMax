package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugIntrcationModel {

    @SerializedName("medicineId")
    @Expose
    private Integer medicineId;
    @SerializedName("interactedId")
    @Expose
    private Integer interactedId;
    @SerializedName("interaction")
    @Expose
    private String interaction;
    @SerializedName("interactionNature")
    @Expose
    private String interactionNature;
    @SerializedName("interactionEffect")
    @Expose
    private String interactionEffect;
    @SerializedName("isLifeThreatning")
    @Expose
    private String isLifeThreatning;
    @SerializedName("suggestiveAction")
    @Expose
    private String suggestiveAction;
    @SerializedName("substitute")
    @Expose
    private String substitute;

    @Override
    public String toString() {
        return "{" +
                "medicineId=" + medicineId +
                ", interactedId=" + interactedId +
                ", interaction='" + interaction + '\'' +
                ", interactionNature='" + interactionNature + '\'' +
                ", interactionEffect='" + interactionEffect + '\'' +
                ", isLifeThreatning='" + isLifeThreatning + '\'' +
                ", suggestiveAction='" + suggestiveAction + '\'' +
                ", substitute='" + substitute + '\'' +
                '}';
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getInteractedId() {
        return interactedId;
    }

    public void setInteractedId(Integer interactedId) {
        this.interactedId = interactedId;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getInteractionNature() {
        return interactionNature;
    }

    public void setInteractionNature(String interactionNature) {
        this.interactionNature = interactionNature;
    }

    public String getInteractionEffect() {
        return interactionEffect;
    }

    public void setInteractionEffect(String interactionEffect) {
        this.interactionEffect = interactionEffect;
    }

    public String getIsLifeThreatning() {
        return isLifeThreatning;
    }

    public void setIsLifeThreatning(String isLifeThreatning) {
        this.isLifeThreatning = isLifeThreatning;
    }

    public String getSuggestiveAction() {
        return suggestiveAction;
    }

    public void setSuggestiveAction(String suggestiveAction) {
        this.suggestiveAction = suggestiveAction;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }
}
