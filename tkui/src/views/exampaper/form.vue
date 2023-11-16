<template>
    <div class="formWrapper">
        <el-form :model="form" label-width="80px" :disabled="formStatus == 2">
            <el-form-item label="试卷名称" style="width: 50%;">
                <el-input v-model="form.sjName"></el-input>
            </el-form-item>
            <el-form-item label="出卷老师" style="width: 50%;">
                <el-input v-model="form.sjTeacher"></el-input>
            </el-form-item>
            <el-form-item label="备注" class="textarea_wrapper">
                <el-input type="textarea" v-model="form.remark" rows="5"></el-input>
            </el-form-item>
            <el-form-item label="上传附件" class="textarea_wrapper">
                <FileUpload :fileObj="fileList" @handle-uploadSuccess="handleUploadSuccess" @after-delete="afterDelete">
                </FileUpload>
            </el-form-item>
        </el-form>
        <div style="display: flex;align-items: center;justify-content: center;margin-top: 200px;">
            <el-button type="primary" @click="save" v-show="formStatus != 2">保存</el-button>
            <div style="width: 100px;"></div>
        <el-button @click="back">返回</el-button>
        </div>

    </div>
</template>

<script>
import { examPaperDetail, examPaperEdit, examPaperAdd, addFile, delFileItem, getFile } from "@/api/exam.js";
export default {
    data() {
        return {
            form: {
                id: "",
                sjName: "",
                sjTeacher: "",
                remark: ""
            },
            formStatus: 0,
            fileList: [],
        }
    },
    mounted() {
        if (this.$route.query.type != 0) {
            examPaperDetail(this.$route.query.id).then((res) => {
                this.formStatus = this.$route.query.type;
                this.form = res.data;
                getFile({ bizType: "1", bizId: this.form.id }).then((response) => {
                    this.fileList = response.data;
                })
            })
        }
    },
    methods: {
        handleUploadSuccess(res) {
            var sendobj = {};
            sendobj.fileName = res.originalFilename;
            sendobj.fileUrl = res.url;
            this.fileList.push(sendobj);
        },
        afterDelete() {

        },
        save() {
            if (this.$route.query.type == 0) {
                examPaperAdd(this.form).then((res) => {
                    this.$message.success("保存成功");
                    let data = {
                        bizType: "1",
                        bizId: res.data.id,
                        bsFileList: this.fileList,
                    };
                    addFile(data).then((response) => {
                        this.$router.go(-1)
                    })
                })
            } else {
                examPaperEdit(this.form).then((res) => {
                    this.$message.success("保存成功");
                    let data = {
                        bizType: "1",
                        bizId: this.form.id,
                        bsFileList: this.fileList,
                    };
                    addFile(data).then((response) => {
                        this.$router.go(-1)
                    })
                })
            }

        },
        back() {
            this.$router.go(-1)
        }
    }
}
</script>

<style lang="scss" scoped>
.formWrapper{
    box-sizing: border-box;
    padding: 50px 150px;
}
.textarea_wrapper{
    width: 100%;
}
.el-form{
    display: flex;
    flex-wrap: wrap;
}
</style>