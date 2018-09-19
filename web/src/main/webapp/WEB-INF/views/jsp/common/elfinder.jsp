<%--
  Created by IntelliJ IDEA.
  User: hanqf
  Date: 2018/8/8
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>elFinder 2.1</title>

    <script src="webjars/jquery/1.12.4/jquery.min.js"></script>

<!-- jQuery and jQuery UI (REQUIRED) -->
<link rel="stylesheet" type="text/css" href="${_contextPath}/resource/css/elfinder/jquery-ui.min.css">
<script src="${_contextPath}/resource/js/elfinder/jquery-ui.min.js"></script>

<!-- elFinder JS AND CSS (REQUIRED) -->
<script src="${_contextPath}/resource/js/elfinder/elfinder.full.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${_contextPath}/resource/css/elfinder/elfinder.full.css" type="text/css" media="screen" charset="utf-8">
<link rel="stylesheet" type="text/css" href="${_contextPath}/resource/css/elfinder/theme.css">

<!-- elFinder translation (OPTIONAL) -->
<script src="${_contextPath}/resource/js/elfinder/i18n/elfinder.zh_CN.js"></script>

<%--<!-- jQuery and jQuery UI (REQUIRED) -->--%>
<%--<link rel="stylesheet" type="text/css" href="webjars/jquery-ui-themes/1.11.4/smoothness/jquery-ui.min.css">--%>

<%--<script src="webjars/jquery-ui/1.11.4/jquery-ui.min.js"></script>--%>

<%--<!-- elFinder CSS (REQUIRED) -->--%>
<%--<link rel="stylesheet" type="text/css" href="webjars/elfinder/2.1.11/css/elfinder.min.css">--%>
<%--<link rel="stylesheet" type="text/css" href="webjars/elfinder/2.1.11/css/theme.css">--%>

<%--<!-- elFinder JS (REQUIRED) -->--%>
<%--<script src="webjars/elfinder/2.1.11/js/elfinder.min.js"></script>--%>

<%--<!-- elFinder translation (OPTIONAL) -->--%>
<%--<script src="webjars/elfinder/2.1.11/js/i18n/elfinder.zh_CN.js"></script>--%>

<script type="text/javascript" charset="utf-8">

    $(document).ready(function() {
        $('#elfinder').elfinder({
            url : 'elfinder-servlet/connector',
            lang: 'zh_CN',                   // language (OPTIONAL)
            height: 680,
            // rememberLastDir: false,
            // reloadClearHistory: true,
            // useBrowserHistory: false,
            // commands: [
            //     'editimage','archive', 'back', 'chmod',  'copy', 'cut', 'download', 'duplicate', 'edit', 'extract',
            //     'forward', 'fullscreen', 'getfile', 'home', 'info', 'mkdir', 'mkfile', 'netmount', 'netunmount',
            //     'open', 'opendir', 'paste', 'places', 'quicklook', 'reload', 'rename', 'resize', 'restore', 'rm',
            //     'sort', 'up', 'upload', 'view'
            // ],
            // contextmenu: {
            //     // navbarfolder menu
            //     navbar: ['editimage', 'open', '|', 'mkdir', 'rename', 'rm', '|', 'info'],
            //     // current directory menu
            //     cwd: ['editimage', 'reload', 'back', 'rename', '|', 'upload', 'mkdir', 'mkfile', 'paste', '|', 'sort', '|', 'info'],
            //     // current directory file menu
            //     files: ['editimage', 'open', 'getfile', '|', 'custom', 'quicklook', '|', 'download', 'rm', '|', 'rename', 'resize', '|', 'archive', 'extract', '|', 'info']
            // }
        });
    });
</script>
</head>

<body>

<!-- Element where elFinder will be created (REQUIRED) -->
<div id="elfinder"></div>

</body>
</html>


