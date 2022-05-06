package com.example.facebook;

public class Profiles {
    private String id;
    private String name;
    private String posts;
    private int imageResource;
    private int friendsCount;

    public Profiles(String name, String posts, int imageResource, int friendsCount) {
        this.name = name;
        this.posts = posts;
        this.imageResource = imageResource;
        this.friendsCount = friendsCount;
    }

    public Profiles() {
    }

    public String getName() {
        return name;
    }
    public String getPosts() {
        return posts;
    }
    public int getImageResource() {
        return imageResource;
    }
    public int getFriendsCount() {
        return friendsCount;
    }

    public String _getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
