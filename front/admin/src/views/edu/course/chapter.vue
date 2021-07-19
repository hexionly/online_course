<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>
    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="openVideo(chapter.id)">
              添加课时</el-button
            >
            <el-button style="" type="text" @click="openEditChapter(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="removeChapter(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>
        <!-- 小节 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <!-- <el-button type="text">编辑</el-button> -->
                <el-button type="text" @click="removeVideo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/eduvod/video/uploadAliyunVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，<br />
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";

export default {
  data() {
    return {
      saveBtnDisabled: false,
      courseId: "",
      chapterVideoList: [],
      chapter: {
        //封装章节数据
        title: "",
        sort: 0
      },
      video: {
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: "",
        videoOriginalName: "" //视频名称
      },
      dialogChapterFormVisible: false,
      dialogVideoFormVisible: false,

      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址
    };
  },
  created() {
    //获取路由id
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideo();
    }
  },
  methods: {
    //上传视频
    //点击×调用这个方法
    beforeVodRemove(file) {
      return this.$confirm(`确定移除${file.name}?`);
    },
    //点击确定
    handleVodRemove() {
      //调用接口删除视频方法
      video.deleteAlyVideo(this.video.videoSourceId)
      .then(()=>{
          //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "视频删除成功"
        });
        //文件列表清空
        this.fileList=[]
        //视频删除之后把数据库中的视频id和名称清空
        this.video.videoSourceId=""
        this.video.videoOriginalName=""
      })
    },
    //成功回调
    handleVodUploadSuccess(response, file) {
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName=file.name
    },
    //视图上传多于一个视频
    handleUploadExceed() {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },

    //=======================小节操作===============================
    openVideo(chapterId) {
      this.dialogVideoFormVisible = true;

      //清空
      this.fileList=[]
      this.video = {};
  
      //获取章节id
      this.video.chapterId = chapterId;
    },
    //增添小节
    addVideo() {
      //设置课程id
      this.video.courseId = this.courseId;
      video.addVideo(this.video).then(response => {
        //关闭弹窗
        this.dialogVideoFormVisible = false;

        //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "添加课时成功"
        });

        //刷新页面
        this.getChapterVideo();
      });
    },
    //修改video
    updateVideo() {},
    //保存或修改
    saveOrUpdateVideo() {
      //   if (!this.video.id) {
      this.addVideo();
      //   } else {
      //     this.updateVideo();
      //   }
    },
    //删除小节
    removeVideo(id) {
      //删除讲师
      this.$confirm("此操作将删除课时纪录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        video.deleteVideo(id).then(response => {
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          //返回列表
          this.getChapterVideo();
        });
      });
    },

    //=======================章节操作===============================
    //删除章节
    removeChapter(chapterId) {
      //删除讲师
      this.$confirm("此操作将删除章节纪录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        chapter.deleteChapter(chapterId).then(response => {
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          //返回列表
          this.getChapterVideo();
        });
      });
    },
    //弹出添加章节页面
    openChapterDialog() {
      this.dialogChapterFormVisible = true;

      //清空
      this.chapter.title = "";
      this.chapter.sort = 0;
    },
    //添加章节
    addChapter() {
      //添加课程id到chapter里边
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then(response => {
        //关闭弹窗
        this.dialogChapterFormVisible = false;

        //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "添加章节成功"
        });

        //刷新页面
        this.getChapterVideo();
      });
    },
    //修改章弹框数据回显
    openEditChapter(chapterId) {
      //打开弹窗
      this.dialogChapterFormVisible = true;
      chapter.getChapter(chapterId).then(response => {
        this.chapter = response.data.chapter;
      });
    },
    //修改章节
    updateChapter() {
      chapter.updateChapter(this.chapter).then(response => {
        //关闭弹窗
        this.dialogChapterFormVisible = false;

        //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "修改章节成功"
        });

        //刷新页面
        this.getChapterVideo();
      });
    },
    saveOrUpdate() {
      //判断修改还是添加
      if (!this.chapter.id) {
        this.addChapter();
      } else {
        this.updateChapter();
      }
    },
    //根据课程id查询章节小节
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then(response => {
        this.chapterVideoList = response.data.allChapterVideo;
      });
    },

    //====================页面操作==================================
    // 上一步
    previous() {
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    // 下一步
    next() {
      this.$router.push({ path: "/course/publish/" + this.courseId });
    }
  }
};
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}
.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>
