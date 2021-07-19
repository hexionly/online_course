<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 课程讲师  -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss'"
          class="avatar-uploader"
        >
          <img
            :src="courseInfo.cover"
            style="width:200px;height:150px"
            alt=""
          />
        </el-upload>
      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />
        元
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存并下一步
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";

export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        title: "",
        subjectId: "",
        subjectParentId: "",
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover: "/static/cover.jpg",
        price: 0
      },
      courseId: "",
      teacherList: [],
      subjectOneList: [],
      subjectTwoList: [],
      BASE_API: process.env.BASE_API // 接口API地址
    };
  },
  created() {
    //获取路由id
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getInfo();
    } else {
      this.getListTeacher();
      this.getOneSubject();
    }
  },
  watch: {
    //监听
    $route(to, from) {
      this.init();
    }
  },
  methods: {
    init() {
      //判断路径是否有id值
      if (this.$route.params && this.$route.params.id) {
        //从路径获取id值
        const id = this.$route.params.id;
        //调用根据id查询的方法
        this.getInfo(id);
      } else {
        //路径没有id值时，做添加
        //清空表单
        this.courseInfo = { cover: "/static/cover.jpg" };
      }
    },
    //根据课程id查询信息
    getInfo() {
      course.getCourseInfoId(this.courseId).then(response => {
        this.courseInfo = response.data.courseInfoVo;
        //查询所有分类，包含一级二级
        subject.getSubjectList().then(response => {
          this.subjectOneList; //获取所有的一级分类
          this.subjectOneList = response.data.list;
          //遍历一级分类
          for (let i = 0; i < this.subjectOneList.length; i++) {
            //获取每个一级分类
            const oneSubject = this.subjectOneList[i];
            //比较course info里边的一级id和所有的以及分类id
            if (this.courseInfo.subjectParentId == oneSubject.id) {
              //获取一级分类中的所有二级分类
              this.subjectTwoList = oneSubject.children;
            }
          }
        });
        //初始化所有讲师
        this.getListTeacher();
      });
    },
    //上传成功
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },
    //上传之前，规定允许上传什么格式
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    //当选择某个一级分类，触发二级分类
    subjectLevelOneChanged(value) {
      //value是一级分类id值
      //   alert(value)
      //遍历所有分类，包含一二级
      for (let i = 0; i < this.subjectOneList.length; i++) {
        //比较一级分类id和value的值
        if (this.subjectOneList[i].id === value) {
          //获取一级分类的二级分类
          this.subjectTwoList = this.subjectOneList[i].children;
          //改变时清空二级分类
          this.courseInfo.subjectId = "";
        }
      }
    },
    //查询所有的以及分类
    getOneSubject() {
      subject.getSubjectList().then(response => {
        this.subjectOneList = response.data.list;
      });
    },
    //查询所有讲师
    getListTeacher() {
      course.getListTeacher().then(response => {
        this.teacherList = response.data.items;
      });
    },
    //添加课程
    addCourse() {
      course.addCourseInfo(this.courseInfo).then(response => {
        //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "添加课程信息成功"
        });
        // 跳转到第二步
        this.$router.push({
          path: "/course/chapter/" + response.data.courseId
        });
      });
    },
    //修改课程
    updateCourse() {
      course.updateCourseInfo(this.courseInfo).then(response => {
        //提示信息
        this.loading = false;
        this.$message({
          type: "success",
          message: "修改课程信息成功"
        });
        // 跳转到第二步
        this.$router.push({
          path: "/course/chapter/" + this.courseId
        });
      });
    },
    //保存或修改
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        //没有id做添加
        this.addCourse();
      } else {
        //有做修改
        this.updateCourse();
      }
    }
  }
};
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
