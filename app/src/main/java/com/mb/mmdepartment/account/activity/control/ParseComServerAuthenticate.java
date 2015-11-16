package com.mb.mmdepartment.account.activity.control;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Handles the comminication with Parse.com
 *
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class ParseComServerAuthenticate implements ServerAuthenticate {
    private Map<String, String> paramas = new HashMap<>();
    private String authtoken = null;
    @Override
    public void userSignUp(final String name, String password, String again_pass, String tel, String code, String authType, final RegistResponse registResponse) throws Exception {
        paramas.put(BaseConsts.APP, CatlogConsts.Regist.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Regist.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Regist.params_sign);
        paramas.put("username", name);
        paramas.put("password", password);
        paramas.put("password1",again_pass);
        paramas.put("phone",tel);
        paramas.put("code", code);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                registResponse.onFailueRequess(request,e);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                registResponse.onResponseSuccess(response);
            }
        });
    }

    @Override
    public void userSignIn(String user, String pass, String authType,RequestListener listener) throws Exception {
    }
}
