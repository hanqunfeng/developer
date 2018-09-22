package org.pyf.developer.elfinder.param;

import lombok.Data;

@Data
public class Node {
    private String source = "fileSystem";
    private String alias;
    private String path;
    private String locale;
    private boolean locked = false;
    private boolean readable = true;
    private boolean writable = true;

}
