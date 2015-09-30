package com.example.rssreader.db;

import com.example.rssreader.Person;

/**
 * Created by Hideyuki.Kikuma on 15/09/30.
 */
public class PersonEntity extends Person {
    public static final long UNDEFINED = -1;
    private long id;

    private PersonEntity(long id, String name, int age, String comment) {
        super(name, age, comment);
        this.id = id;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public static class Builder {
        private long id = UNDEFINED;
        private String name;
        private int age;
        private String comment;

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public PersonEntity build() {
            return new PersonEntity(id, name, age, comment);
        }
    }
}
