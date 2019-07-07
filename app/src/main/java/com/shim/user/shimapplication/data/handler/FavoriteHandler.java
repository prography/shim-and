package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.FavoriteResponse;

public interface FavoriteHandler extends BaseHandler {
    void onSuccessSendFavorite(FavoriteResponse response);
}
