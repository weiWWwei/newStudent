<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.number"
            placeholder="考号"
            clearable
          ></el-input>
          <el-input
            v-model="searchModel.uid"
            placeholder="身份证号"
            clearable
          ></el-input>
          <el-button
            @click="getStudentList"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-download"
            @click="onexcel"
          >
            导出
          </el-button>
          <el-button
            @click="filterStudentList"
            type="primary"
            round
            icon="el-icon-refresh-right"
            >筛选</el-button
          >
        </el-col>
        <el-col :span="4" align="right">
          <el-button
            @click="openEditUI(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card>
      <el-table :data="studentList" style="width: 100%">
        <el-table-column label="#" width="60">
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column label="考号" prop="number" width="200">
        </el-table-column>
        <el-table-column label="身份证" prop="uid" width="200">
        </el-table-column>
        <el-table-column label="姓名" prop="name"> </el-table-column>
        <el-table-column label="语文" prop="chinese"> </el-table-column>
        <el-table-column label="数学" prop="math"> </el-table-column>
        <el-table-column label="英语" prop="english"> </el-table-column>
        <el-table-column label="物理" prop="physics"> </el-table-column>
        <el-table-column label="化学" prop="chemistry"> </el-table-column>
        <el-table-column label="生物" prop="biology"> </el-table-column>
        <el-table-column label="总分" prop="score"> </el-table-column>
        <el-table-column label="操作" align="right">
          <template slot-scope="scope">
            <el-button
              @click="openEditUI(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="studentForm" ref="studentFormRef" :rules="rules">
        <el-form-item label="名字" prop="name" :label-width="formLabelWidth">
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考号" prop="number" :label-width="formLabelWidth">
          <el-input v-model="studentForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="uid" :label-width="formLabelWidth">
          <el-input v-model="studentForm.uid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="语文" :label-width="formLabelWidth">
          <el-input v-model="studentForm.chinese" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数学" :label-width="formLabelWidth">
          <el-input v-model="studentForm.math" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="英语" :label-width="formLabelWidth">
          <el-input v-model="studentForm.english" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="物理" :label-width="formLabelWidth">
          <el-input v-model="studentForm.physics" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="生物" :label-width="formLabelWidth">
          <el-input v-model="studentForm.biology" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="化学" :label-width="formLabelWidth">
          <el-input
            v-model="studentForm.chemistry"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import studentApi from "@/api/student";
export default {
  data() {
    var checkNumber = (rule, value, callback) => {
      var reg = /\d{11}$/;
      if (!reg.test(value)) {
        return callback(new Error("考号格式错误"));
      }
      callback();
    };
    var checkUid = (rule, value, callback) => {
      var reg =
        /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!reg.test(value)) {
        return callback(new Error("身份证格式错误"));
      }
      callback();
    };
    return {
      downloadLoading: false,
      formLabelWidth: "130px",
      studentForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      studentList: [],
      rules: {
        name: [
          { required: true, message: "请输入名字", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "长度在 2 到 10 个字符",
            trigger: "blur",
          },
        ],
        number: [
          { required: true, message: "请输入考号", trigger: "blur" },
          { validator: checkNumber, trigger: "blur" },
        ],
        uid: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          { validator: checkUid, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    onexcel() {
      const { export_json_to_excel } = require("@/excle/Export2Excel"); // 将生成excel的Export2Excel.js引入

      let title = [
        "考号",
        "身份证号",
        "姓名",
        "语文",
        "数学",
        "英语",
        "物理",
        "化学",
        "生物",
        "总分",
      ]; // 这里是excel表格的表头
      let key = [
        "number",
        "uid",
        "name",
        "chinese",
        "math",
        "english",
        "physics",
        "biology",
        "chemistry",
        "score",
      ]; // 设置生成列的数据属性
      function formatJson(filterVal, jsonData) {
        return jsonData.map((v) => filterVal.map((j) => v[j]));
      }

      export_json_to_excel(
        title,
        formatJson(key, this.studentList),
        "学生信息"
      ); // 调用生成方法
    },
    saveStudent() {
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          studentApi.saveStudent(this.studentForm).then((response) => {
            this.$message({
              message: response.message,
              type: "success",
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getStudentList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    clearForm() {
      this.studentForm = {};
      this.$refs.studentFormRef.clearValidate();
    },
    openEditUI(id) {
      if (id == null) {
        this.title = "新增学生";
      } else {
        this.title = "修改学生信息";
        studentApi.getStudentById(id).then((response) => {
          this.studentForm = response.data;
        });
      }

      this.dialogFormVisible = true;
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getStudentList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getStudentList();
    },
    filterStudentList() {
      studentApi.filterStudentList(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
    getStudentList() {
      studentApi.getStudentList(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getStudentList();
  },
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input {
  width: 80%;
}
</style>