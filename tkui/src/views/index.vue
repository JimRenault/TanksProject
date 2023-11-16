<template>
  <div class="mainWrapper">
    <div class="searchArea">
      <div class="searchItem"><span>试卷名称:</span><el-input v-model="params.sjName" placeholder="请填写名称"></el-input></div>
      <div class="searchButton">
        <el-button @click="search" type="primary">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </div>
    </div>
    <div class="buttonArea">
      <el-button @click="testPaperAdd" type="primary">新增</el-button>
    </div>
    <div class="tableArea">
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column type="index" label="序号" width="60px" align="center">
        </el-table-column>
        <el-table-column prop="sjName" label="试卷名称" align="center">
        </el-table-column>
        <el-table-column prop="sjTeacher" label="出卷教师" align="center">
        </el-table-column>
        <el-table-column prop="createBy" label="创建人" align="center">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center">
        </el-table-column>
        <el-table-column label="操作" width="300px" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)" type="primary">编辑</el-button>
            <el-button size="mini" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageArea">
        <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" background>
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import { getListData, examPaperDelete } from "@/api/exam.js";
export default {
  data() {
    return {
      params: {
        pageNum: 1,
        pageSize: 10,
        sjName: "",
      },
      total: 0,
      tableData: []
    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    pageChange(page) {
      this.params.pageNum = page;
      this.getList();
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        sjName: "",
      }
      this.getList();
    },
    search() {
      getListData(this.params).then((res) => {
        this.tableData = res.rows;
        this.total = res.total;
      })
    },
    getList() {
      getListData(this.params).then((res) => {
        this.tableData = res.rows;
        this.total = res.total
      })
    },
    testPaperAdd() {
      this.$router.push({
        path: "/form",
        query: {
          id: "",
          type: 0
        }
      });
    },
    handleEdit(index, row) {
      this.$router.push({
        path: "/form",
        query: {
          id: row.id,
          type: 1
        }
      });
    },
    handleDetail(index, row) {
      this.$router.push({
        path: "/form",
        query: {
          id: row.id,
          type: 2
        }
      });
    },
    handleDelete(index, row) {
      examPaperDelete(row.id).then((res) => {
        this.$message.success("删除成功!");
        this.getList();
      })
    }
  }
}
</script>

<style scoped lang="scss">
.mainWrapper {
  min-height: calc(100vh - 84px);
  height: 100%;
  width: 100%;
  box-sizing: border-box;
  padding: 30px;
}

.searchArea {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .searchItem {
    font-size: 14px;

    .el-input {
      width: 260px;
      margin-left: 10px;
    }
  }
}

.buttonArea {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.tableArea {
  height: 400px;
}

.pageArea {
  margin-top: 40px;
  height: 100px;
  display: flex;
  justify-content: flex-end;
}
</style>