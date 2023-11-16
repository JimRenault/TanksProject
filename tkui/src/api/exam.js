import request from '@/utils/request'

//获取列表数据
export function getListData(query) {
    return request({
        url: '/system/paper/list',
        method: 'get',
        params: query
    })
}
//试卷新增
export function examPaperAdd(data) {
    return request({
        url: '/system/paper',
        method: 'post',
        data: data
    })
}
//试卷编辑
export function examPaperEdit(data) {
    return request({
        url: '/system/paper',
        method: 'put',
        data: data
    })
}
//试卷查询
export function examPaperDetail(id) {
    return request({
        url: '/system/paper/' + id,
        method: 'get',
    })
}
//试卷删除
export function examPaperDelete(id) {
    return request({
        url: '/system/paper/' + id,
        method: 'delete',
    })
}
export function addFile(query) {
    return request({
      url: "/file/" + query.bizType + "/" + query.bizId,
      method: "post",
      data: query.bsFileList,
    });
  }
  export function delFileItem(id) {
    return request({
      url: "/file/" + id,
      method: "delete",
    });
  }
  export function getFile(query) {
    return request({
      url: "/file/" + query.bizType + "/" + query.bizId,
      method: "get",
    });
  }
  //下载文件
export function downloadFile(query) {
    //获取文件类型
    const file_type = query.fileUrl.split(".")[1];
    // console.log("file_type", file_type);
    let contentType = "application/octet-stream"; //'application/octet-stream;charset=UTF-8'
    //根据文件拓展名 设置文件响应头
    if (file_type === "doc" || file_type === "docx") {
      contentType = "application/msword;charset=UTF-8";
    } else if (file_type === "pdf") {
      contentType = "application/pdf;charset=UTF-8";
    } else if (file_type === "xls" || file_type === "xlsx") {
      contentType = "application/vnd.ms-excel;charset=UTF-8";
    }
    console.log("contentType == " + contentType);
    return request({
      url: query.fileUrl,
      method: "get",
      data: query,
      responseType: "blob",
    }).then((res) => {
      // console.log("res", res, contentType);
      const blob = new Blob([res], {
        type: contentType,
      });
      //const fileName = name
      if ("download" in document.createElement("a")) {
        // 非IE下载
        const elink = document.createElement("a");
        elink.download = decodeURI(query.fileName); //
        elink.style.display = "none";
        elink.href = URL.createObjectURL(blob);
        document.body.appendChild(elink);
        elink.click();
        URL.revokeObjectURL(elink.href); // 释放URL 对象
        document.body.removeChild(elink);
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, query.fileName);
      }
    });
  }