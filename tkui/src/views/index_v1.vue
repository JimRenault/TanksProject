<template>
  <div class="mainWrapper">
    <div class="tanksWorld" id="tanksWorld">
      <div class="startGame gameopaction" style="margin-top: 200px;" @click="clickNewGame">
        开始新游戏
      </div>
      <div class="loadingGame gameopaction" @click="loadingGame">
        载入游戏
      </div>
      <div class="signOut gameopaction">
        退出
      </div>
      <div class="gameSaved_Wrapper" v-if="showSavedListStatus">
        <div class="gameSaved">
          <div class="savedTitle">
            游戏存档
          </div>
          <div class="gameSavedList">
            <div class="gameSaveItem" v-for="(item, idx) in gameSavedData" :key="idx">
              <div class="leftArea">
                <div style="width: 100%;">
                  <span style="font-weight: bold;">存档名称:</span><span style="vertical-align: -1px; margin-left: 5px;">{{
                    item.filesName }}</span>
                </div>
                <div style="width: 100%;">
                  <span style="font-weight: bold;">保存时间:</span><span style="vertical-align: -1px; margin-left: 5px;">{{
                    item.updateTine }}</span>
                </div>
              </div>
              <div class="rightArea">
                <i class="iconfont icon-weibiaoti518" title="载入游戏" @click="loadingThisGame(item)"></i>
                <i class="iconfont icon-bianji" title="修改存档名称" @click="editGameloadName(item)"></i>
                <i class="iconfont icon-shanchu" title="删除存档" @click="deleteThisGame(item)"></i>
              </div>
            </div>
          </div>
          <el-button type="info" size="mini" class="closeButton" @click="showSavedListStatus = false">关闭</el-button>
        </div>
      </div>
      <canvas id="canvas" v-if="gameisRunning" @click="getpostTion($event)" @keydown="test()">

      </canvas>
    </div>
    <div class="actionList" v-if="gameisRunning">
      <div class="createTanks" @click="createTanks">
        创建坦克
      </div>
      <div @click="deleteTanks">
        删除坦克
      </div>
      <div>
        坦克数量:{{ tanksNum }}
      </div>
      <div @click="backToHome">
        返回主菜单
      </div>
    </div>
    <div class="hideLayer" v-if="tankgameFormStatus">

    </div>
    <div class="hideLayer" v-if="gameFormStatus">

    </div>
    <div class="choosePositon" v-if="isChooseStatus">

    </div>
    <div class="tankForm" v-if="tankgameFormStatus">
      <div class="formTitle">
        坦克名称
      </div>
      <el-input v-model="tankForm.tkName" placeholder="请输入内容"></el-input>
      <!-- <el-input v-model="tankForm.tkType" placeholder="请输入内容"></el-input> -->
      <i class="iconfont icon-weizhi" title="选取生成位置" @click="chooseBornPostition"
        style="cursor: pointer; color: #ffffff;"></i>
      <div class="saveGame gameformbtn" @click="submitTank">保存</div>
      <div class="closeGame gameformbtn" @click="closeFormtank">关闭</div>
    </div>
    <!-- <div class="gameForm" v-if="gameFormStatus">
      <div class="formTitle">
        存档名称
      </div>
      <el-input v-model="gameLoadForm.filesName" placeholder="请输入内容"></el-input>
      <div class="saveGame gameformbtn" @click="submitGame()">保存</div>
      <div class="closeGame gameformbtn" @click="closeForm">关闭</div>
    </div> -->
    <div>
      <i v-for="(item, idx) in tankList" class="iconfont icon-kejichangcheng-tankepingtai"
        :style="{ left: item.tkX + 'px', top: item.tkY + 'px' }" :title="item.tkName" @dblclick="selectThisTank(item)"
        :id="'T-' + item.tkId" v-if="gameisRunning"></i>
      <i id="ammo"></i>
    </div>
  </div>
</template>

<script>
import { getList, addGameLoad, editGameLoad, deleteGameLoad, getTanksList, edittanks, addtanks, deletetanks } from "@/api/game.js";
export default {
  data() {
    return {
      isChooseStatus: false,
      tankList: [],
      formType: 0,
      nowGameId: null,//当前游戏实例ID
      showSavedListStatus: false,//游戏存档面板状态
      gameSavedData: [
      ],
      tanksNum: 0,
      gameLoadForm: {
        filesName: "",
        updateTine: "",
        filesId: "",
      },
      tankForm: {
        tkId: "",
        filesId: "",
        tkName: "",
        tkType: "",
        tkX: "",
        tkY: "",
        bloodVolume: "",
        aggressivity: "",
        updateTime: "",
      },
      gameFormStatus: false,
      gameisRunning: false,
      tankgameFormStatus: false,
      iscontrol: false,
      nowcontrolTankId: null,
      nowTankDomId: "",
      otherTankPositionList: []
    }
  },
  methods: {
    deleteTanks() {
      if (this.nowcontrolTankId == null) {
        this.$message.warning("请先选择要删除的坦克");
        return
      }
      deletetanks(this.nowcontrolTankId).then((res) => {
        this.$message.success("删除成功");
        this.getTanksList();
        this.nowcontrolTankId = null;
        this.iscontrol = false;
      })
    },
    resStyle() {
      var domArray = document.getElementsByClassName("icon-kejichangcheng-tankepingtai");
      for (let i = 0; i < domArray.length; i++) {
        domArray[i].style.color = "#ffffff";
        domArray[i].style.fontWeight = "normal";
      }
    },
    selectThisTank(tankItem) {
      this.nowTankDomId = "";
      this.resStyle();
      this.iscontrol = true;
      this.nowcontrolTankId = tankItem.tkId;
      this.nowTankDomId = "T-" + tankItem.tkId;
      document.getElementById(this.nowTankDomId).style.color = "red";
      document.getElementById(this.nowTankDomId).style.fontWeight = "900";
    },
    getTanksList() {
      getTanksList({ filesId: this.nowGameId }).then((res) => {
        this.tankList = res.data;
        this.tanksNum = res.data.length;

      })
    },
    getpostTion(event) {
      if (!this.isChooseStatus) {
        return
      }
      var canvas = document.getElementById("canvas");
      var left = canvas.offsetLeft;
      var top = canvas.offsetTop;
      if (event.clientX < left || event.clientY < top) {
        this.$message.warning("请在界面内选取生成位置")
        return
      }
      this.tankForm.tkX = event.clientX;
      this.tankForm.tkY = event.clientY;
      this.$message.success("选取成功");
      this.isChooseStatus = false;
      this.tankgameFormStatus = true;
    },
    resetFormTank() {
      this.tankForm = {
        tkId: "",
        filesId: "",
        tkName: "",
        tkType: "",
        tkX: "",
        tkY: "",
        bloodVolume: "",
        aggressivity: "",
        updateTime: "",
      }
    },
    chooseBornPostition() {
      this.tankgameFormStatus = false;
      this.isChooseStatus = true;
    },
    createTanks() {
      this.tankgameFormStatus = true;
    },
    closeFormtank() {
      this.tankgameFormStatus = false;
      this.resetFormTank();
    },
    submitTank(isShowTip) {
      this.tankForm.filesId = this.nowGameId;
      if (this.tankForm.tkName == "") {
        this.$message.warning("请输入坦克名称")
        return
      }
      if (this.tankForm.tkX == "" || this.tkY == "") {
        this.$message.warning("未选取生成位置,请选择生成位置")
        return
      }
      addtanks(this.tankForm).then((res) => {
        if(isShowTip) {
          this.$message("创建成功");
        }
        this.resetFormTank();
        this.tankgameFormStatus = false;
        this.getTanksList();
      })
    },
    loadingThisGame(data) {
      this.nowGameId = data.filesId;
      this.showSavedListStatus = false;
      this.gameisRunning = true;
      this.getTanksList();
    },
    editGameloadName(data) {
      this.gameLoadForm = data;
      this.formType = 1;
      this.gameFormStatus = true;
    },
    deleteThisGame(data) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteGameLoad(data.filesId).then((res) => {
          this.getAllGamesLoad();
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    backToHome() {
      this.gameisRunning = false;
      this.resStyle();
      this.resetForm();
      this.tankList = [];
    },
    submitGame() {
      if (this.gameLoadForm.filesName == "") {
        this.$message("请填写存档名称")
        return
      }
      if (this.formType == 0) {
        addGameLoad(this.gameLoadForm).then((res) => {
          this.$message.success("保存成功");
          this.getAllGamesLoad();
          this.nowGameId = res.data.filesId;
          this.gameisRunning = true;
          this.gameFormStatus = false;
          this.resetForm();
        })
      } else {
        editGameLoad(this.gameLoadForm).then((res) => {
          this.$message.success("保存成功");
          this.getAllGamesLoad();
          this.nowGameId = res.data.filesId;
          this.gameFormStatus = false;
          this.resetForm();
        })
      }
    },
    closeForm() {
      this.gameFormStatus = false;
      this.resetForm();
    },
    resetForm() {
      this.gameLoadForm = {
        filesName: "",
        updateTine: "",
        filesId: "",
      }
    },
    getAllGamesLoad() {
      //获取游戏存档列表
      getList().then((res) => {
        this.gameSavedData = res.data;
      })
    },
    clickNewGame() {
      // 开始游戏并创建游戏存档
      // this.gameFormStatus = true;
      this.formType = 0;

      //随机生成存档文件名称
      this.gameLoadForm.filesName = "file" + new Date().getTime().toString()

      //开始游戏
      this.submitGame()

      for (let index = 0; index < 3; index++) {
        setTimeout(() => {
          this.tankForm.tkName = new Date().getTime().toString()
          this.tankForm.tkX = 600 + (Math.random() * 100 * (index * 1.2))
          this.tankForm.tkY = 200 + (Math.random() * 80 * index)
          this.submitTank(index == 0)
        }, 200)
      }

    },
    // 载入游戏
    loadingGame() {
      // this.showSavedListStatus = true;
      this.nowGameId = this.gameSavedData[this.gameSavedData.length - 1].filesId;
      this.showSavedListStatus = false;
      this.gameisRunning = true;
      this.getTanksList();
    },
  },
  mounted() {
    this.getAllGamesLoad();
    document.addEventListener("keydown", (event) => {
      if (!this.iscontrol) {
        return
      }
      var canvas = document.getElementById("canvas");
      var left = canvas.offsetLeft;
      var top = canvas.offsetTop;
      // var bottom = canvas.
      console.log(left, "ct")
      var topSize = Number((document.getElementById(this.nowTankDomId).style.top).slice(0, -2));
      var leftSize = Number((document.getElementById(this.nowTankDomId).style.left).slice(0, -2));

      let direction = "top"; //当前坦克方向

      if (event.keyCode == 87 || event.keyCode == 38) {
        //前进
        if (topSize == top) return
        direction = "top"
        topSize--;
        document.getElementById(this.nowTankDomId).style.top = topSize + "px";
        document.getElementById(this.nowTankDomId).style.transform = "rotate(0)";
        document.getElementById("ammo").style.transform = "rotate(0)"
      }
      else if (event.keyCode == 83 || event.keyCode == 40) {
        //后退
        if (topSize == (top + 700 - 15)) return
        direction = "bottom"
        topSize += 1;
        document.getElementById(this.nowTankDomId).style.transform = "rotate(180deg)";
        document.getElementById(this.nowTankDomId).style.top = topSize + "px";
        document.getElementById("ammo").style.transform = "rotate(180deg)"
      } else if (event.keyCode == 65 || event.keyCode == 37) {
        if (leftSize == left) return
        direction = "left"
        leftSize--;
        document.getElementById(this.nowTankDomId).style.transform = "rotate(-90deg)";
        document.getElementById(this.nowTankDomId).style.left = leftSize + "px";
        document.getElementById("ammo").style.transform = "rotate(-90deg)"
      } else if (event.keyCode == 68 | event.keyCode == 39) {
        if (leftSize == (left + 1300 - 15)) return
        direction = "right"
        leftSize += 1;
        document.getElementById(this.nowTankDomId).style.transform = "rotate(90deg)";
        document.getElementById(this.nowTankDomId).style.left = leftSize + "px";
        document.getElementById("ammo").style.transform = "rotate(90deg)"
      } else if (event.keyCode == 32) {

        console.log("发射子弹")
        const center = -(16 / 2);
        let tTop = topSize;
        let tLeft = leftSize - center;
        document.getElementById("ammo").style.opacity = 1;

        if (direction == "top" || direction === "bottom") {
          document.getElementById("ammo").style.top = tTop + "px";
          document.getElementById("ammo").style.left = tLeft + "px";
        } else {
          ocument.getElementById("ammo").style.top = top + "px";
          document.getElementById("ammo").style.left = tTop + "px";
        }

        let count = 0
        let timer = setInterval(() => {

          if (direction === "top") {
            tTop -= 10;
          } else if (direction === "bottom") {
            tTop += 10;
          } else if (direction === "left") {
            tLeft -= 10;
          } else if (direction === "right") {
            tLeft += 10;
          }

          if (direction == "top" || direction == "bottom") {
            document.getElementById("ammo").style.top = tTop + "px";
          } else {
            document.getElementById("ammo").style.left = tLeft + "px";
          }
          count++;
          if (count > 20) {
            clearInterval(timer);
            timer = null;
            document.getElementById("ammo").style.opacity = 0;
          }
        }, 10)
      }
      console.log(topSize, leftSize)
    })
    document.addEventListener("keyup", (event) => {
      if (!this.iscontrol) {
        return
      }
      var topSize = Number((document.getElementById(this.nowTankDomId).style.top).slice(0, -2));
      var leftSize = Number((document.getElementById(this.nowTankDomId).style.left).slice(0, -2));
      if (event.keyCode == 87 || event.keyCode == 38) {
        //前进
        console.log("w");
        var sendObj = {
          tkId: this.nowcontrolTankId,
          filesId: this.nowGameId,
          tkName: "",
          tkType: "",
          tkX: leftSize,
          tkY: topSize,
          bloodVolume: "0",
          aggressivity: "",
          updateTime: "",
        }
        edittanks(sendObj).then((res) => {
          console.log(res);
        })
      }
      else if (event.keyCode == 83 || event.keyCode == 40) {
        //后退
        console.log("s");
        var sendObj = {
          tkId: this.nowcontrolTankId,
          filesId: this.nowGameId,
          tkName: "",
          tkType: "",
          tkX: leftSize,
          tkY: topSize,
          bloodVolume: "1",
          aggressivity: "",
          updateTime: "",
        }
        edittanks(sendObj).then((res) => {
          console.log(res);
        })
      } else if (event.keyCode == 65 || event.keyCode == 37) {
        //左
        var sendObj = {
          tkId: this.nowcontrolTankId,
          filesId: this.nowGameId,
          tkName: "",
          tkType: "",
          tkX: leftSize,
          tkY: topSize,
          bloodVolume: "2",
          aggressivity: "",
          updateTime: "",
        }
        console.log("a");
        edittanks(sendObj).then((res) => {
          console.log(res);
        })
      } else if (event.keyCode == 68 || event.keyCode == 39) {
        //右
        var sendObj = {
          tkId: this.nowcontrolTankId,
          filesId: this.nowGameId,
          tkName: "",
          tkType: "",
          tkX: leftSize,
          tkY: topSize,
          bloodVolume: "3",
          aggressivity: "",
          updateTime: "",
        }
        edittanks(sendObj).then((res) => {
          console.log(res);
        })
      }

    })

  }
}
</script>

<style lang="scss" scoped>
.hideLayer {
  height: 100vh;
  width: 100vw;
  background-color: rgba($color: #000000, $alpha: 0.4);
  position: fixed;
  z-index: 100;
}

.choosePositon {
  height: 100vh;
  width: 100vw;
  background-color: rgba($color: #000000, $alpha: 0.4);
  position: fixed;
  z-index: 80;
}

.tankForm {
  position: fixed;
  width: 400px;
  height: 200px;
  background: #409EFF;
  left: 0;
  bottom: 0;
  right: 0;
  top: 0;
  margin: auto;
  z-index: 111;
  box-sizing: border-box;
  padding: 20px;

  .formTitle {
    height: 40px;
    font-size: 32px;
    font-weight: 800;
    color: #ffffff;
    line-height: 40px;
    text-align: center;
    margin-bottom: 20px;
  }

  .gameformbtn {
    height: 30px;
    width: 80px;
    background: #ffffff;
    text-align: center;
    line-height: 30px;
    cursor: pointer;
    color: #409EFF;
    font-weight: bold;
    display: inline-block;
    margin-left: 60px;
    margin-top: 30px;
  }
}

.gameForm {
  position: fixed;
  width: 400px;
  height: 200px;
  background: #409EFF;
  left: 0;
  bottom: 0;
  right: 0;
  top: 0;
  margin: auto;
  z-index: 111;
  box-sizing: border-box;
  padding: 20px;

  .formTitle {
    height: 40px;
    font-size: 32px;
    font-weight: 800;
    color: #ffffff;
    line-height: 40px;
    text-align: center;
    margin-bottom: 20px;
  }

  .gameformbtn {
    height: 30px;
    width: 80px;
    background: #ffffff;
    text-align: center;
    line-height: 30px;
    cursor: pointer;
    color: #409EFF;
    font-weight: bold;
    display: inline-block;
    margin-left: 60px;
    margin-top: 30px;
  }
}

#canvas {
  height: 700px;
  width: 1200px;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  z-index: 99;
  margin: auto;
  background: #7CD140;
  transition: 2s;
  cursor: pointer;
}

.mainWrapper {
  height: 100vh;
  width: 100vw;
}

.tanksWorld {
  z-index: 98;
  width: 1200px;
  height: 700px;
  background-image: url(../assets/images/tanks2.png);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  margin: auto;
  box-sizing: border-box;
}

.gameopaction {
  font-size: 30px;
  font-weight: 900;
  margin: 0 auto;
  text-align: center;
  color: #ffffff;
  cursor: pointer;
  margin-bottom: 50px;
}

.gameopaction:hover {
  font-size: 38px;
  transition: 0.2s linear;
  //text-shadow: 0 0 10px rgba(255, 255, 255, 0.2);
  //animation: glow 1.5s infinite;
}

@keyframes glow {
  0% {
    text-shadow: 0 0 25px rgba(255, 255, 255, 0.2);
  }

  50% {
    text-shadow: 0 0 50px rgba(255, 255, 255, 0.8);
  }

  100% {
    text-shadow: 0 0 25px rgba(255, 255, 255, 0.2);
  }
}

.gameSaved_Wrapper {
  width: 1200px;
  height: 700px;
  background: rgba($color: #000000, $alpha: 0.3);
  position: fixed;
  left: 0;
  bottom: 0;
  top: 0;
  right: 0;
  margin: auto;
}

.gameSaved {
  background: rgba($color: #ffffff, $alpha: 0.8);
  border: 2px solid #8A3F4D;
  height: 500px;
  width: 450px;
  position: fixed;
  left: 0;
  bottom: 0;
  top: 0;
  right: 0;
  margin: auto;

  .savedTitle {
    height: 45px;
    text-align: center;
    line-height: 45px;
    color: #000000;
    font-weight: bold;
    font-size: 24px;
  }

  .gameSavedList {
    height: calc(100% - 85px);
    box-sizing: border-box;
    margin: 15px;
    overflow-y: auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-content: flex-start;
  }

  .gameSaveItem {
    width: 400px;
    height: 80px;
    box-sizing: border-box;
    background: #409EFF;
    display: flex;
    margin-bottom: 10px;
  }

  .gameSaveItem:hover {
    background: #106dca;
  }

  .leftArea {
    height: 100%;
    width: 300px;
    box-sizing: border-box;
    padding: 20px;

    div {
      color: #ffffff;
    }
  }

  .rightArea {
    height: 100%;
    width: 120px;
    display: flex;
    align-items: center;
    justify-content: space-around;

    .iconfont {
      cursor: pointer;
      color: #ffffff;
    }

    .icon-shanchu {
      color: #e21818;
    }
  }
}

.closeButton {
  position: absolute;
  right: 0;
  left: 0;
  bottom: -50px;
  margin: auto;
  height: 30px;
  width: 50px;
  padding: 0;

}

.gameSavedList::-webkit-scrollbar {
  width: 4px;
}

.gameSavedList::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2);
}

.gameSavedList::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
}

.actionList {
  width: 150px;
  height: 250px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  position: fixed;
  right: 100px;
  bottom: calc(50% - 125px);
  background-color: #B9971A;
  align-content: space-around;

  div {
    cursor: pointer;
    background: #3DACFF;
    color: #ffffff;
    font-weight: bold;
    width: 100px !important;
    height: 40px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.icon-kejichangcheng-tankepingtai {
  position: fixed;
  z-index: 103;
  color: #ffffff;
  font-weight: normal;
}

#ammo {
  z-index: 9999;
  display: block;
  position: absolute;
  width: 2px;
  height: 10px;
  background-color: #ffffff;
  opacity: 0;
}
</style>
