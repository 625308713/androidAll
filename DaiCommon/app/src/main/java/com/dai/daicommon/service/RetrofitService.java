package com.dai.daicommon.service;

import com.dai.daicommon.service.bean.Book;
import com.dai.daicommon.service.bean.Person;
import com.dai.daicommon.service.bean.VersionBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {
    //请求版本信息
    @GET("jsonapi/update_test.json")
    Observable<VersionBean> getVersionInfo();

    //已确定入参名称
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);
    //多个不确定入参
    @GET("book/search")
    Observable<Book> getSearchBook2(@QueryMap Map<String, String> options);

    //请求url中间有个值是变量，需要用Path注解
    @GET("group/{id}/users")
    Observable<Book> groupList(@Path("id") int groupId, @Query("sort") String sort);

    //指定一个对象作为HTTP请求体
    @POST("users/new")
    Observable<Book> createBook(@Body Book book);

    @POST("main/login")
    Observable<Person> testMethod(@Body Map<String, String> map);
}
