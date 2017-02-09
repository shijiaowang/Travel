package com.yunspeak.travel.bean

import com.yunspeak.travel.global.TravelsObject

/**
 * Created by wangyang on 2016/10/17 0017.
 */

class HomeBean : TravelsObject() {


    var data: DataBean? = null

    class DataBean {

        var activit: ActivityBean? = null
        var banner: List<BannerBean>? = null
        var forum: List<ForumBean>? = null
        var destination: List<DestinationBean>? = null
        var find_travel: List<FindTravelBean>? = null
        var index_text: List<IndexTextBean>? = null

        class BannerBean {
            var id: String? = null
            var path: String? = null
            var article_id: String? = null
            var title: String? = null
            var url: String? = null
        }

        class ForumBean {
            var id: String? = null
            var title: String? = null
            var circle_img: String? = null
            var cname: String? = null
            var cid: String? = null
            var index_img: String? = null
        }

        class DestinationBean {
            var id: String? = null
            var title: String? = null
            var province: String? = null
            var city: String? = null
            var address: String? = null
            var logo_img: String? = null
        }

        class FindTravelBean {
            var id: String? = null
            var title: String? = null
            var author: String? = null
            var logo_img: String? = null
            var title_img: String? = null
        }

        class IndexTextBean {
            var type: Int = 0
            var title: String? = null
            var img: String? = null
            var url: String? = null
        }
    }
}
