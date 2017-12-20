package com.company.vo;

/**
 * Created by Administrator on 2017/11/30.
 */
public class ProductImageVO {

    private String uri;
    private String url;

    @Override
    public String toString() {
        return "ProductImageVO{" +
                "uri='" + uri + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductImageVO() {

    }

    public ProductImageVO(String uri, String url) {

        this.uri = uri;
        this.url = url;
    }
}
