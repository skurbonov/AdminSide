package com.example.sardorbek.adminside.Model;

import java.util.List;

/**
 * Created by sardorbek on 4/25/18.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    public List<Result> results;
}
