<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/static/01.xls'">点击下载模版</a>
        </el-tag>
      </el-form-item>

      <!-- limit限制每次上传的文件数 -->
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/eduservice/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel"
        >
          <el-button slot="trigger" size="small" type="primary"
            >选取文件</el-button
          >
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload"
            >上传到服务器
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      //   OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
      //   fileUploadBtnText: "上传到服务器", // 按钮文字
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false
    };
  },
  created() {},
  methods: {
    //点击上传按钮
    submitUpload() {
      this.importBtnDisabled = true;
      this.loading = true;
      //上传提交
      //js:document.getElementById("upload").submit()
      this.$refs.upload.submit();
    },
    //上传成功
    fileUploadSuccess() {
      //提示信息
      this.loading = false;
      this.$message({
        type: "success",
        message: "添加课程信息成功"
      });

      //添加成功，页面跳转到列表
      this.$router.push({path:'subject/list'})
    },
    //上传失败
    fileUploadError() {
      //提示信息
      this.loading = false;
      this.$message({
        type: "error",
        message: "导入失败"
      });
    }
  }
};
</script>
