<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.number" placeholder="考号"></el-input>
          <el-input v-model="searchModel.uid" placeholder="身份证号"></el-input>
          <el-button type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <el-button type="primary" icon="el-icon-plus" circle></el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card>
      <el-table :data="studentList" style="width: 100%">
        <el-table-column label="#" type="index" width="60"> </el-table-column>
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
        <el-table-column label="操作" align="right"> </el-table-column>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      studentList: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄",
        },
      ],
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handleSizeChange() {},
    handleCurrentChange() {},
  },
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
</style>