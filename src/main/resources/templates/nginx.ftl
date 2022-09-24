 server {
        #定义当前这个server监听的端口
        listen       80;
        #定义使用localhost访问
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        #默认请求地址，如果请求是：192.168.10.80:80/  那么会进入这个里面的tomcat反向代理地址
        #一个location里面只能有一个proxy_pass
        location / {
        #此处可以配置Tomcat反向代理地址比如：
        #此处可以引用上面upstream 的多台tomcat；也可以单独配置一台
        proxy_pass http://127.0.0.1:8081/; #配置单台
        #proxy_pass http://webServer/; #引用上面的多台
        #引用上面的多台配置
           # root   html; #默认的网站根目录的位置
            #index  index.html index.htm; #网站的欢迎页,起始页
        }


        #表示如果请求是：192.168.10.80:80/web 那么会进入这个里面的tomcat反向代理地址
        location /web {
        #此处引用上面的配置的多台tomcat
        #proxy_pass http://127.0.0.1:8082/;
        #proxy_pass http://web2Server/; #引用上面的多台Tomcat配置
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #

        #错误提示页面
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }