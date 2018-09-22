package org.pyf.developer.elfinder.param;


import java.util.List;

public class ElfinderConfiguration {

    private Thumbnail thumbnail;

    private List<Node> volumes;

    private Long maxUploadSize = -1L;//默认不限制

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Node> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Node> volumes) {
        this.volumes = volumes;
    }

    public Long getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(Long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }
}
