(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-9909dea2"],{"21bb":function(t,e,a){"use strict";var s=a("2dad"),n=a.n(s);n.a},"2dad":function(t,e,a){},bb51:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"home"},[a("md-tab-bar",{attrs:{items:t.tabbarTitles},on:{change:t.onTabChange},model:{value:t.current,callback:function(e){t.current=e},expression:"current"}}),a("md-field",{staticClass:"home-search"},[a("md-input-item",{attrs:{type:"text",placeholder:"大象的分布区域","is-amount":"","is-highlight":""}},[a("i",{staticClass:"md-icon icon-font md-icon-search search lg",attrs:{slot:"right"},slot:"right"})])],1),a("md-swiper",{ref:"swiper",attrs:{autoplay:0,"is-prevent":!1,"is-loop":!1,"has-dots":!1},on:{"before-change":t.onSwiperChange}},[a("md-swiper-item",[a("md-field",{staticClass:"main-recommend-area"},[a("md-field",{staticClass:"banner"})],1),a("md-field",{staticClass:"classify-btn-area"},[a("el-row",{attrs:{gutter:20}},t._l(t.cbtns,(function(e){return a("el-col",{key:e.name,attrs:{span:6}},[a("div",{staticClass:"classify-btn"},[a("el-avatar",{attrs:{size:100,src:e.img,fit:"cover"}}),a("label",[t._v(t._s(e.name))])],1)])})),1)],1),a("router-view",{attrs:{path:"/list"}})],1),a("md-swiper-item",[t._v(" 又是一年，她又遇到了他，他正牵着孩子的手，走的飞快。 ")])],1)],1)},n=[],i={name:"home",data:function(){return{tabbarTitles:[{name:0,label:"推荐"},{name:1,label:"动物界"},{name:2,label:"植物界"},{name:3,label:"细菌界"},{name:4,label:"真菌界"},{name:5,label:"色素界"},{name:6,label:"病毒"},{name:7,label:"原生动物界"},{name:8,label:"古细菌界"}],current:0,mainSwiperImgs:[{src:"http://picture.ik123.com/uploads/allimg/161203/3-1612030ZG5.jpg",text:"称霸森林的动物"},{src:"http://5b0988e595225.cdn.sohucs.com/images/20180620/e7e33799729847828e79b684b1be6479.jpg",text:"兔子"},{src:"http://pic1.win4000.com/wallpaper/4/543f715e9a2ab.jpg?down",text:"鸟"}],cbtns:[{name:"濒临灭绝",img:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3591085026,2645681060&fm=26&gp=0.jpg"},{name:"本地特有",img:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3591085026,2645681060&fm=26&gp=0.jpg"},{name:"最近发现",img:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3591085026,2645681060&fm=26&gp=0.jpg"},{name:"世界之最",img:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3591085026,2645681060&fm=26&gp=0.jpg"}]}},methods:{onSwiperChange:function(t,e){this.current=e},onTabChange:function(t,e){this.$refs.swiper.goto(e)}}},c=i,r=(a("21bb"),a("2877")),l=Object(r["a"])(c,s,n,!1,null,null,null);e["default"]=l.exports}}]);