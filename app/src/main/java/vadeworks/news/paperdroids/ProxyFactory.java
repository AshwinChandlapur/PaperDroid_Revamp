package vadeworks.news.paperdroids;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * Created by ashwinchandlapur on 17/03/18.
 */

public class ProxyFactory {

    private static HttpProxyCacheServer sharedProxy;

    public ProxyFactory() {
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        return sharedProxy == null ? (sharedProxy = newProxy(context)) : sharedProxy;
    }

    private static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context);
    }
}
