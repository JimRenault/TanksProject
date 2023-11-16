<template>
  <div class="upload-file">
    <el-upload multiple :action="uploadFileUrl" :before-upload="handleBeforeUpload" :file-list="fileList"
      :on-error="handleUploadError" :on-success="handleUploadSuccess" :show-file-list="false" :headers="headers"
      class="upload-file-uploader" ref="fileUpload" :disabled="uploadStatus">
      <!-- 上传按钮 -->
      <el-button size="mini" type="primary" icon="el-icon-upload
" :disabled="uploadStatus">{{ title }}
      </el-button>
      <span v-show="fileListStyle"> {{ protalFileText }}</span>
    </el-upload>
    <div class="box" v-if="!fileListStyle">
      <el-row v-for="(item, index) in fileObjList" :key="index" class="rowClass">
        <el-col :span="6">
          <div class="textOver" :title="item.fileName" @click="downFile(item, index)">
            {{ item.fileName }}
          </div>
        </el-col>
        <el-button size="mini" @click="delFile(item, index)" type="danger"
          v-if="$route.query.type == 'detail' ? false : true" style="color: #ffffff;">
          删除
        </el-button>
      </el-row>
    </div>
    <div v-else>
      <div class="imgBox" v-for="(item, idx) in fileObjList" :key="idx" v-show="showThesmallImg">
        <div class="imgSelf">
          <img :src="$store.state.user.imgUrl + item.fileUrl" alt="" />
          <div class="deleteArea">
            <span @click="delFile(item, idx)" style="color: #ffffff;">
              删除
            </span>

          </div>
        </div>
      </div>
    </div>
    <el-dialog title="pdf预览" width="60%" :visible.sync="isShowDialog">
      <div style="height: 700px;width:100%;">
        <object :data="'http://47.93.78.30:85/prod-api' + videoUrl" type="application/pdf" width="100%"
          height="100%"></object>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { delFileItem, downloadFile } from "@/api/exam.js";
export default {
  name: "FileUpload",
  props: {
    showThesmallImg: {
      type: Boolean,
      default: true,
    },
    uploadStatus: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "上传附件",
    },
    protalFileText: {
      //门户页面图片封面上传按钮侧面提示语
      type: String,
      default: "",
    },
    //文件列表数据
    fileObj: {
      type: Array,
      default: [],
    },
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 5,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    fileType: {
      type: Array,
      // default: () => ["doc", "xls", "ppt", "txt", "pdf", "png", "jpg", "webp"],
      default: () => ["pdf",],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true,
    },
    fileListStyle: {
      //文件列表样式
      type: Boolean,
      default: false,
    },
    clearFileObj: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      videoUrl: '',
      isShowDialog: false,
      proxyUrl: process.env.VUE_APP_BASE_API,
      uploadTitle: "上传附件",
      fileObjList: [],
      number: 0,
      uploadList: [],
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传文件服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
    };
  },
  mounted() { },
  watch: {
    clearFileObj(newVal) {
      this.fileObjList = [];
    },
    title(newVal) {
      this.uploadTitle = newVal;
    },
    fileObj(newVal) {
      if (this.fileListStyle) {
        if (newVal.length != 0) {
          // this.fileObjList = newVal;
          var list = [];
          list[0] = newVal[0];
          this.fileObjList = list;

        } else {
          this.fileObjList = [];
        }
      } else {
        if (newVal.length != 0) {
          this.fileObjList = newVal;
        } else {
          this.fileObjList = [];
        }
      }
    },
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(",");
          // 然后将数组转为对象数组
          this.fileList = list.map((item) => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true,
    },
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    downFile(item, index) {
      console.log('item', item)
      if (item.fileType == "application/pdf") {
        this.videoUrl = item.fileUrl
        this.isShowDialog = !this.isShowDialog
      } else {
        downloadFile(item);
      }
    },
    delFile(item, index) {
      if (item.fileId != "") {
        this.fileObj.splice(index, 1);
      } else {
        delFileItem(item.fileId).then((res) => {
          //删除之后方法
          this.$emit("after-delete", res);
        });
      }
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      if (file.type != "application/pdf") {
        this.$message({
          message: "只允许上传文件形式为pdf形式的文件",
          type: "warning",
        });
        return false;
      }
      if (file.size > 52428800) {
        this.$message({
          message: "文件大小已超过限制",
          type: "warning",
        });
        return false;
      }
      this.$emit("handle-BeforeUpload", file);
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading();
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      this.$emit("handle-uploadSuccess", res, file);
    },
    // 删除文件
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 上传结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != "" ? strs.substr(0, strs.length - 1) : "";
    },
  },
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}

.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}

.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}

.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}

.box {
  height: 100px;
  width: 100%;
  border: 1px solid #dee1e7;
  border-radius: 3px;
  overflow: scroll;
  //修改按钮样式

  .rowClass {
    position: relative;

    .el-button {
      height: 30px;
      position: absolute;
      right: 20px;
      top: 0;
      bottom: 0;
      margin: auto;
    }

    .el-button--primary {
      right: 80px;
    }
  }
}

.box:hover {
  border-color: #c0c4cc;
}

::-webkit-scrollbar {
  /*隐藏滚轮*/
  display: none !important;
}

.rowClass {
  padding: 0 10px;
}

.rowClass:hover {
  background: #f2f8fe;
}

.textOver {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  min-width: 100px;
  cursor: pointer;
}

.textOver:hover {
  text-decoration: underline #358cf3;
  color: #358cf3;
}
</style>
