package com.ftinc.lol52.api.model;

import android.text.TextUtils;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Date;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

/**
 * Created by r0adkll on 5/13/15.
 */
@JsonObject
@Table("commits")
public class LolCommit extends Model {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    @JsonField
    @Column("message")
    public String message;

    @JsonField
    @Column("repo")
    public String repo;

    @JsonField
    @Column("author_name")
    public String authorName;

    @JsonField
    @Column("author_email")
    public String authorEmail;

    @JsonField
    @Column("hash")
    public String commitHash;

    @JsonField
    @Column("opt_key")
    public String optionalKey;

    @JsonField
    @Column("image_url")
    public String imageUrl;

    @JsonField
    @Column("timestamp")
    public Long timestamp;

    /***********************************************************************************************
     *
     * Methods
     *
     */

    public String getRepo(){
        if(TextUtils.isEmpty(repo) || repo.trim().equalsIgnoreCase("/")){
            return optionalKey;
        }
        return repo;
    }

    public Date getTime(){
        return new Date(timestamp * 1000L);
    }

    /**
     * Convert object into string representation
     */
    @Override
    public String toString() {
        return String.format("Webhook [image: %s][repo: %s][message: %s][author: %s %s][commit: %s][opt: %s]",
                imageUrl, repo, message, authorName, authorEmail, commitHash, optionalKey);
    }


    /***********************************************************************************************
     *
     * Builder
     *
     */

    /**
     * Builder class
     */
    public static class Builder{

        private LolCommit lc;

        public Builder(){
            lc = new LolCommit();
        }

        public Builder message(String msg){
            lc.message = msg;
            return this;
        }

        public Builder repo(String repo){
            lc.repo = repo;
            return this;
        }

        public Builder author(String name, String email){
            lc.authorName = name;
            lc.authorEmail = email;
            return this;
        }

        public Builder commit(String hash){
            lc.commitHash = hash;
            return this;
        }

        public Builder opt(String optKey){
            lc.optionalKey = optKey;
            return this;
        }

        public LolCommit build(){
            return lc;
        }

    }

}