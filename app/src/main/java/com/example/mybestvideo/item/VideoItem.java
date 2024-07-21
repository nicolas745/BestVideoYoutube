package com.example.mybestvideo.item;

public class VideoItem {
    private String titre;
    private String texte;
    private int id;
    public VideoItem(String titre, String texte,int id) {
        this.titre = titre;
        this.texte = texte;
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getid() {
        return id;
    }
}
