(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-346e"],{"1Lpw":function(t,e,n){},JCNI:function(t,e,n){"use strict";n.d(e,"i",function(){return c}),n.d(e,"k",function(){return u}),n.d(e,"a",function(){return d}),n.d(e,"f",function(){return f}),n.d(e,"c",function(){return h}),n.d(e,"m",function(){return m}),n.d(e,"h",function(){return p}),n.d(e,"b",function(){return g}),n.d(e,"j",function(){return b}),n.d(e,"l",function(){return v}),n.d(e,"g",function(){return w}),n.d(e,"d",function(){return _}),n.d(e,"e",function(){return k});var a=n("gDS+"),o=n.n(a),i=n("vDqi"),r=n.n(i),s=n("Q2AE"),l=function(t){return JSON.parse(o()(t))};function c(t,e){return t=l(t),r()({method:"get",url:"/page/articles",params:{id:e},headers:{"Content-type":"application/json",Authorization:"bearer "+s.a.state.user.token}})}function u(t){return r()({method:"get",url:"/recommend/list/"+t,headers:{Authorization:"bearer "+s.a.state.user.token}})}function d(t){return r()({method:"post",url:"/article/add/",data:t,headers:{Authorization:"bearer "+s.a.state.user.token}})}function f(t){return r()({method:"delete",url:"/article/",params:{id:t},headers:{Authorization:"bearer "+s.a.state.user.token}})}function h(t,e){return r()({method:"put",url:"/recommend/add/"+e,params:{id:t},headers:{Authorization:"bearer "+s.a.state.user.token}})}function m(t){return r()({method:"post",url:"/recommend/saveList",data:t,headers:{Authorization:"bearer "+s.a.state.user.token}})}function p(t){return r()({method:"delete",url:"/recommend",params:{id:t},headers:{Authorization:"bearer "+s.a.state.user.token}})}function g(t){return r()({method:"post",url:"/catalog/add",data:t,headers:{Authorization:"bearer "+s.a.state.user.token}})}function b(){return r()({method:"get",url:"/catalog/list/",headers:{Authorization:"bearer "+s.a.state.user.token}})}function v(t){return r()({method:"post",url:"/catalog/saveList",data:t,headers:{Authorization:"bearer "+s.a.state.user.token}})}function w(t){return r()({method:"delete",url:"/catalog",params:{id:t},headers:{Authorization:"bearer "+s.a.state.user.token}})}function _(t){return t=l(t),r()({method:"get",url:"/article/list",params:t,headers:{"Content-type":"application/json",Authorization:"bearer "+s.a.state.user.token}})}function k(t){return r()({method:"post",url:"/article/check",data:t,headers:{"Content-type":"application/json",Authorization:"bearer "+s.a.state.user.token}})}},O51g:function(t,e,n){"use strict";var a=n("cqkM");n.n(a).a},cqkM:function(t,e,n){},lYSk:function(t,e,n){"use strict";var a=n("1Lpw");n.n(a).a},nD9G:function(t,e,n){"use strict";n.r(e);var a=n("JCNI"),o=n("U/5H"),i=n.n(o),r={name:"DragTable",filters:{statusFilter:function(t){return{published:"success",draft:"info",deleted:"danger"}[t]}},data:function(){return{showReviewer:!1,dialogFormVisible:!1,list:null,total:null,listLoading:!0,sortable:null,oldList:[],newList:[],catalog:{name:""}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0,Object(a.j)().then(function(e){var n=e.data.data;null!=n&&(t.list=n.map(function(e){return t.$set(e,"edit",!1),e.originalName=e.name,e})),t.listLoading=!1,t.oldList=t.list.map(function(t){return t.id}),t.newList=t.oldList.slice(),t.$nextTick(function(){t.setSort()})})},setSort:function(){var t=this,e=document.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];this.sortable=i.a.create(e,{ghostClass:"sortable-ghost",setData:function(t){t.setData("Text","")},onEnd:function(e){var n=t.list.splice(e.oldIndex,1)[0];t.list.splice(e.newIndex,0,n);var a=t.newList.splice(e.oldIndex,1)[0];t.newList.splice(e.newIndex,0,a)}})},saveCatalogList:function(){var t=this;Object(a.l)(this.list).then(function(e){t.showReviewer=!1,t.$message({showClose:!0,message:"文章分类排序修改成功！",type:"success"})}).catch(function(e){t.$message.error("不好意思，出错了哦~")})},addCatalog:function(){var t=this;Object(a.b)(this.catalog).then(function(e){t.dialogFormVisible=!1,t.catalog.name="",t.getList(),t.$message({showClose:!0,message:"添加文章分类成功！",type:"success"})}).catch(function(e){t.$message.error(e.message)})},deleteCatalog:function(t){var e=this;Object(a.g)(t.id).then(function(t){e.getList(),e.$message({showClose:!0,message:"删除文章分类成功！",type:"success"})}).catch(function(t){e.$message.error("不好意思，出错了哦~")})},updateSortState:function(){this.showReviewer?this.showReviewer=!1:this.showReviewer=!0},cancelEdit:function(t){t.name=t.originalName,t.edit=!1,this.$message({message:"取消修改，分类名已恢复！",type:"warning"})},confirmEdit:function(t){var e=this;t.edit=!1,t.originalName=t.name,Object(a.b)(t).then(function(t){e.$message({showClose:!0,message:"分类名修改成功！",type:"success"})}).catch(function(t){console.log(t.message),e.$message.error()})}},watch:{list:{handler:function(t){t.forEach(function(t,e){t.sequence=e})},deep:!0}}},s=(n("lYSk"),n("O51g"),n("KHd+")),l=Object(s.a)(r,function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("el-dialog",{attrs:{title:"新增文章分类",visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[n("el-form",{attrs:{model:t.catalog}},[n("el-form-item",{attrs:{label:"活动名称"}},[n("el-input",{attrs:{autocomplete:"off"},model:{value:t.catalog.name,callback:function(e){t.$set(t.catalog,"name",e)},expression:"catalog.name"}})],1)],1),t._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),t._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:t.addCatalog}},[t._v("立即创建")])],1)],1),t._v(" "),n("el-button-group",{staticClass:"button-group"},[n("el-button",{attrs:{type:"primary",icon:"el-icon-edit"},on:{click:function(e){t.dialogFormVisible=!0}}},[t._v("新增")]),t._v(" "),n("el-button",{attrs:{type:"primary",icon:"el-icon-share"},on:{click:function(e){t.updateSortState()}}},[t._v("排序")])],1),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:t.list,"row-key":"id",border:"","highlight-current-row":"",width:"500"}},[n("el-table-column",{attrs:{align:"center",label:"ID",width:"65"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("span",[t._v(t._s(e.row.id))])]}}])}),t._v(" "),n("el-table-column",{attrs:{"min-width":"150px",label:"分类名称"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.edit?[n("el-input",{staticClass:"edit-input",attrs:{size:"small",width:"80px"},model:{value:e.row.name,callback:function(n){t.$set(e.row,"name",n)},expression:"scope.row.name"}}),t._v(" "),n("el-button",{staticClass:"cancel-btn",attrs:{size:"small",icon:"el-icon-refresh",type:"warning"},on:{click:function(n){t.cancelEdit(e.row)}}},[t._v("取消")])]:n("span",[t._v(t._s(e.row.name))])]}}])}),t._v(" "),n("el-table-column",{attrs:{width:"280px",align:"center",label:"创建时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("span",[t._v(t._s(e.row.createTime))])]}}])}),t._v(" "),n("el-table-column",{attrs:{width:"90px",align:"center",label:"序号"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("span",[t._v(t._s(e.row.sequence))])]}}])}),t._v(" "),n("el-table-column",{attrs:{width:"150px",align:"center",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.edit?n("el-button",{attrs:{type:"success",size:"small",icon:"el-icon-circle-check-outline"},on:{click:function(n){t.confirmEdit(e.row)}}},[t._v("确定")]):n("el-button-group",[n("el-button",{attrs:{type:"info",size:"small",icon:"el-icon-edit"},on:{click:function(t){e.row.edit=!e.row.edit}}}),t._v(" "),n("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(n){t.deleteCatalog(e.row)}}})],1)]}}])}),t._v(" "),t.showReviewer?n("el-table-column",{attrs:{align:"center",label:"排序",width:"80"},scopedSlots:t._u([{key:"default",fn:function(t){return[n("svg-icon",{staticClass:"drag-handler",attrs:{"icon-class":"drag"}})]}}])}):t._e()],1),t._v(" "),t.showReviewer?n("div",{staticStyle:{margin:"10px 0px"}},[n("el-button",{on:{click:function(e){t.showReviewer=!1}}},[t._v("取消")]),t._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:t.saveCatalogList}},[t._v("确认")])],1):t._e()],1)},[],!1,null,"3cffbcf6",null);l.options.__file="catalogTable.vue";e.default=l.exports}}]);