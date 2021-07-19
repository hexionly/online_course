<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="searchObj.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="searchObj.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="课程标题" />
      </el-form-item>
      <!-- 讲师 -->
      <el-form-item>
        <el-select v-model="searchObj.teacherId" placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="课程名称" width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="课时数" width="70" align="center">
        <template slot-scope="scope">
          {{ scope.row.lessonNum }}
        </template>
      </el-table-column>
      <el-table-column label="课程状态" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.status === "Normal" ? "已发布" : "未发布" }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{
            Number(scope.row.price) === 0
              ? "免费"
              : "¥" + scope.row.price.toFixed(2)
          }}
        </template>
      </el-table-column>
      <el-table-column
        prop="viewCount"
        label="浏览数量"
        width="100"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.viewCount }}次
        </template>
      </el-table-column>
      <el-table-column
        prop="buyCount"
        label="购买次数"
        width="100"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人/次
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程信息</el-button
            >
          </router-link>
          <router-link :to="'/edu/course/chapter/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程大纲</el-button
            >
          </router-link>
          <el-button
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="removeCourseById(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />
  </div>
</template>

<script>
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";

export default {
  data() {
    return {
      listLoading: true, // 是否显示loading信息
      list: null, // 数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      searchObj: {
        subjectParentId: "",
        subjectId: "",
        title: "",
        teacherId: ""
      }, // 查询条件
      teacherList: [], // 讲师列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [] // 二级分类列表
    };
  },
  created() {
    this.fetchData();
    // 初始化分类列表
    this.initSubjectList();
    // 获取讲师列表
    this.initTeacherList();
  },
  methods: {
    //删除课程及课程章节小节视频
    removeCourseById(id) {
      //删除讲师
      this.$confirm("此操作将永久删除此人, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        course.deleteCourse(id).then(() => {
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          this.fetchData();
        });
      });
    },
    fetchData(page = 1) {
      // 调用api层获取数据库中的数据
      this.page = page;
      this.listLoading = true;
      course
        .publishCourseList(this.page, this.limit, this.searchObj)
        .then(response => {
          // debugger 设置断点调试
          if (response.success === true) {
            this.list = response.data.rows;
            this.total = response.data.total;
          }
          this.listLoading = false;
        });
    },
    initTeacherList() {
      course.getListTeacher().then(response => {
        this.teacherList = response.data.items;
      });
    },
    initSubjectList() {
      subject.getSubjectList().then(response => {
        this.subjectNestedList = response.data.list;
      });
    },
    subjectLevelOneChanged(value) {
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === value) {
          this.subSubjectList = this.subjectNestedList[i].children;
          this.searchObj.subjectId = "";
        }
      }
    },
    resetData() {
      this.searchObj = {};
      this.subSubjectList = []; // 二级分类列表
      this.fetchData();
    }
  }
};
</script>
