<template>
  <div>
    <el-input v-model="account" placeholder="请输入账号" class="input-with-select">
      <el-button slot="append" icon="el-icon-search" />
    </el-input>
    <div id="map" ref="map" style="width:90%;height:800px" />
  </div>
</template>
<script>
import { Scene, LineLayer } from '@antv/l7'
import { GaodeMap } from '@antv/l7-maps'

export default {
  name: 'Trajectory',
  data() {
    return {
      account: null,
      scene: null
    }
  },
  created() {
    // setTimeout(() => {
    //   this.initBaiduMap({
    //     lines: [],
    //     effect: []
    //   })
    // }, 500)
    setTimeout(() => {
      this.hqdw()
    }, 500)
  },
  updated() {
    // this.getLocusByPhoneTest() // echarts轨迹
    // 初始化地图的时候在创建是会报错容器id不存在，所以后置初始化
    // this.mapData() // antv L7轨迹
    // this.hqdw()
  },
  methods: {
    // 地图线条数据加载L7
    mapData() {
      // 地图初始化
      this.scene = new Scene({
        id: 'map',
        map: new GaodeMap({
          pitch: 35.210526315789465, // pitch 地图倾角
          style: 'dark',
          center: [104.288144, 31.239692], // center 地图中心
          zoom: 4.4 // zoom 初始化缩放等级 高德 （3-18）
        })
      })
      debugger
      this.scene.on('loaded', () => {
        const dat = `rom,to,value,type,lng1,lat1,lng2,lat2
拉萨,海西,6.91,move_out,91.111891,29.662557,97.342625,37.373799
拉萨,成都,4.79,move_out,91.111891,29.662557,104.067923,30.679943
拉萨,重庆,2.41,move_out,91.111891,29.662557,106.530635,29.544606
拉萨,北京,2.05,move_out,91.111891,29.662557,116.395645,39.929986
拉萨,西宁,1.7,move_out,91.111891,29.662557,101.767921,36.640739
拉萨,上海,1.06,move_out,91.111891,29.662557,121.487899,31.249162
拉萨,西安,1.05,move_out,91.111891,29.662557,108.953098,34.2778
拉萨,昆明,0.62,move_out,91.111891,29.662557,102.714601,25.049153
拉萨,郑州,0.6,move_out,91.111891,29.662557,113.649644,34.75661
拉萨,兰州,0.55,move_out,91.111891,29.662557,103.823305,36.064226
拉萨,成都,4.27,move_in,91.111891,29.662557,104.067923,30.679943
拉萨,海西,2.61,move_in,91.111891,29.662557,97.342625,37.373799
拉萨,重庆,1.72,move_in,91.111891,29.662557,106.530635,29.544606
拉萨,西宁,0.96,move_in,91.111891,29.662557,101.767921,36.640739
拉萨,北京,0.69,move_in,91.111891,29.662557,116.395645,39.929986
拉萨,西安,0.58,move_in,91.111891,29.662557,108.953098,34.2778
拉萨,绵阳,0.55,move_in,91.111891,29.662557,104.705519,31.504701
拉萨,临夏,0.45,move_in,91.111891,29.662557,103.215249,35.598514
拉萨,遂宁,0.4,move_in,91.111891,29.662557,105.564888,30.557491
拉萨,兰州,0.4,move_in,91.111891,29.662557,103.823305,36.064226`
        debugger
        const layer = new LineLayer()
          .source(dat, {
            parser: {
              type: 'csv',
              x: 'lng1',
              y: 'lat1',
              x1: 'lng2',
              y1: 'lat2'
            }
          })
          .size(1)
          .shape('arc3d')
          .color('#FF7C6A')
          .animate({
            enable: true,
            interval: 0.1,
            trailLength: 0.5,
            duration: 2
          })
          .style({
            opacity: 0.8
          })
        this.scene.addLayer(layer)
      })
    },

    /**
     * 初始化地图echarts 轨迹弧线 Bmap初始化在index最外层中引入的百度script的ak
     */
    initBaiduMap(data) {
      if (this.chart) {
        this.chart.dispose()
      }
      debugger
      this.chart = this.$echarts.init(this.$refs.map)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: function(params) {
            return params.name
          }
        },
        bmap: {
          center: [120.453663, 36.395591],
          zoom: 12,
          roam: true,
          mapStyle: {
            style: 'midnight'
          }
        },
        series: [
          {
            type: 'lines',
            coordinateSystem: 'bmap',
            zlevel: 1,
            progressive: 200,
            progressiveThreshold: 500,
            effect: {
              show: true,
              period: 6,
              trailLength: 0.7,
              color: '#fff',
              symbolSize: 4
            },
            lineStyle: {
              normal: {
                color: '#ffa022',
                width: 0,
                curveness: 0.2
              }
            },
            data: data.lines
          },
          {
            type: 'lines',
            coordinateSystem: 'bmap',
            zlevel: 2,
            symbol: ['none', 'arrow'],
            symbolSize: 10,
            effect: {
              show: true,
              period: 6,
              trailLength: 0,
              symbol: 'none',
              symbolSize: 15
            },
            lineStyle: {
              color: '#ffa022',
              width: 2,
              opacity: 0.6,
              curveness: 0.2
            },
            data: data.lines
          },
          {
            type: 'effectScatter',
            coordinateSystem: 'bmap',
            zlevel: 2,
            rippleEffect: {
              brushType: 'stroke'
            },
            symbolSize: 10,
            itemStyle: {
              color: '#ffa022'
            },
            data: data.effect
          }
        ]
      }

      this.chart.setOption(option)
    },

    getLocusByPhoneTest() {
      const list = [
        {
          creatime: '1581135050',
          longitude: '120.436145',
          latitude: '36.390682'
        },
        {
          creatime: '1581135350',
          longitude: '120.436289',
          latitude: '36.396608'
        },
        {
          creatime: '1581135650',
          longitude: '120.446493',
          latitude: '36.397073'
        },
        {
          creatime: '1581135950',
          longitude: '120.461154',
          latitude: '36.398467'
        },
        {
          name: '李白',
          idcard: '37082911111111111',
          phone: '18396836873',
          creatime: '1581136250',
          longitude: '120.462016',
          latitude: '36.404045'
        }
      ]
      this.dataConvert(list)
    },

    /**
     * 数据转换
     */
    dataConvert(res) {
      debugger
      const formatterOptions = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric'
      }
      res.forEach((item, index) => {
        const creatime = item.creatime
        const event = new Date(parseInt(creatime) * 1000)

        item.creatime = event.toLocaleDateString(undefined, formatterOptions)
      })
      this.userInfo = [res[res.length - 1]]
      this.locusList = res

      const lines = []
      const effect = []
      let from = null

      if (res.length > 0) {
        from = res[0]
        effect.push({
          name: from.creatime,
          value: [from.longitude, from.latitude]
        })

        if (res.length > 1) {
          res.splice(0, 1)
        }

        res.forEach(item => {
          const coords = []

          coords.push([from.longitude, from.latitude])

          coords.push([item.longitude, item.latitude])

          lines.push({
            coords: coords
          })

          effect.push({
            name: item.creatime,
            value: [item.longitude, item.latitude]
          })

          from = item
        })

        this.initBaiduMap({
          lines,
          effect
        })
      }
    },
    /*global BMap*/
    /*global BMAP_STATUS_SUCCESS*/
    hqdw() {
      var _this = this
      var map = new BMap.Map('map')
      var point = new BMap.Point(116.331398, 39.897445)
      map.centerAndZoom(point, 12)

      var geolocation = new BMap.Geolocation()
      // 开启SDK辅助定位
      geolocation.enableSDKLocation()
      geolocation.getCurrentPosition(function(r) {
        if (this.getStatus() === BMAP_STATUS_SUCCESS) {
          var mk = new BMap.Marker(r.point)
          map.addOverlay(mk)
          map.panTo(r.point)
          alert('您的位置：' + r.point.lng + ',' + r.point.lat)
          // console.log('您的位置：' + r.point.lng + ',' + r.point.lat)
        } else {
          // alert('failed' + this.getStatus())
          console.log('失败' + this.getStatus())
          _this.hqdw()
        }
      })
    }
  }
}
</script>

<style lang="stylus" scoped>

</style>
