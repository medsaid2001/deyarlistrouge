package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

public class TemplateList {
    private boolean activateFullImageRetrieving = false;
    private ArrayList<MorphoImage> morphoImages = new ArrayList<>();
    private ArrayList<TemplateFVP> templateFVPList = new ArrayList<>();
    private ArrayList<Template> templateList = new ArrayList<>();

    public void putTemplate(Template template) {
        this.templateList.add(template);
    }

    public void setImage(MorphoImage morphoImage) {
        this.morphoImages.add(morphoImage);
    }

    public MorphoImage getImage(int i) {
        if (i < this.morphoImages.size()) {
            return this.morphoImages.get(i);
        }
        return null;
    }

    public int getNbTemplate() {
        return this.templateList.size();
    }

    public Template getTemplate(int i) {
        if (i < this.templateList.size()) {
            return this.templateList.get(i);
        }
        return null;
    }

    public void putFVPTemplate(TemplateFVP templateFVP) {
        this.templateFVPList.add(templateFVP);
    }

    public int getNbFVPTemplate() {
        return this.templateFVPList.size();
    }

    public TemplateFVP getFVPTemplate(int i) {
        if (i < this.templateFVPList.size()) {
            return this.templateFVPList.get(i);
        }
        return null;
    }

    public boolean isActivateFullImageRetrieving() {
        return this.activateFullImageRetrieving;
    }

    public void setActivateFullImageRetrieving(boolean z) {
        this.activateFullImageRetrieving = z;
    }
}
