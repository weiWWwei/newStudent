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
            @click="getStudent"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
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
      </el-table>
    </el-card>
  </div>
</template>

<script>
import studentApi from "@/api/student";
export default {
  data() {
    return {
      studentList: [],
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
    };
  },
  methods: {
    getStudent() {
      studentApi.getStudent(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
};
</script>

<style>
</style>