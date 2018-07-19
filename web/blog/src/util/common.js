/**
 * Created by SNOW on 2018.03.05.
 */
function GetElementBottomMiddlePosition (id) {
  const ele = document.getElementById(id)
  if (ele) {
    let rect = ele.getBoundingClientRect()
    console.log('getElementBottomMiddlePosition rect', rect)
    let _x = rect.left + ele.scrollLeft + rect.width / 2
    let _y = rect.bottom + ele.scrollTop
    return {x: _x, y: _y}
  } else {
    return null
  }
}

function ImgLoader (url, ready, load, error) {
  console.log('图片加载方法', url)
  let onready
  let width
  let height
  let newWidth
  let newHeight
  let img = new Image()
  img.src = url

  // 如果图片被缓存，则直接返回缓存数据
  if (img.complete) {
    ready && ready.call(img)
    load && load.call(img)
    console.log('已经加载过该图')
    return
  }

  width = img.width
  height = img.height

  // 加载错误后的事件
  img.onerror = function () {
    console.log('onerror执行')
    error && error.call(img)
    onready.end = true
    img = img.onload = img.onerror = null
  }
  // 图片尺寸就绪
  onready = function () {
    newWidth = img.width
    newHeight = img.height
    if (newWidth !== width || newHeight !== height ||
      // 如果图片已经在其他地方加载可使用面积检测
      newWidth * newHeight > 1024
    ) {
      console.log('当前图片', img)
      ready && ready.call(img)
      onready.end = true
    }
  }
  onready()

  // 完全加载完毕的事件
  img.onload = function () {
    console.log('onload执行')
    // onload在定时器时间差范围内可能比onready快
    // 这里进行检查并保证onready优先执行
    !onready.end && onready()
    load && load.call(img)
    // IE gif动画会循环执行onload，置空onload即可
    img = img.onload = img.onerror = null
  }
}

// Helper functions.
let getContext = function (width, height) {
  let canvas = document.createElement('canvas')
  canvas.setAttribute('width', width)
  canvas.setAttribute('height', height)
  return canvas.getContext('2d')
}

let getImageData = function (img, loaded) {
  let imgObj = new Image()
  let imgSrc = img.src || img

  // Can't set cross origin to be anonymous for data url's
  // https://github.com/mrdoob/three.js/issues/1305
  if (imgSrc.substr(0, 5) !== 'data:') {
    imgObj.crossOrigin = 'Anonymous'
  }

  imgObj.onload = function () {
    let context = getContext(imgObj.width, imgObj.height)
    context.drawImage(imgObj, 0, 0)

    let imageData = context.getImageData(0, 0, imgObj.width, imgObj.height)
    loaded && loaded(imageData.data)
  }
  imgObj.src = imgSrc
}

let makeRGB = function (name) {
  return ['rgb(', name, ')'].join('')
}

let mapPalette = function (palette) {
  let arr = []
  for (let prop in palette) { arr.push(frmtPobj(prop, palette[prop])) }
  arr.sort(function (a, b) { return (b.count - a.count) })
  return arr
}

let fitPalette = function (arr, fitSize) {
  if (arr.length > fitSize) {
    return arr.slice(0, fitSize)
  } else {
    for (let i = arr.length - 1; i < fitSize - 1; i++) { arr.push(frmtPobj('0,0,0', 0)) }
    return arr
  }
}

let frmtPobj = function (a, b) {
  return {name: makeRGB(a), count: b}
}

// RGBaster Object
// ---------------
//
let PALETTESIZE = 10

function GetImgRGBaster (img, opts) {
  opts = opts || {}
  let exclude = opts.exclude || [] // for example, to exclude white and black:  [ '0,0,0', '255,255,255' ]
  let paletteSize = opts.paletteSize || PALETTESIZE

  getImageData(img, function (data) {
    let colorCounts = {}
    let rgbString = ''
    let rgb = []
    // let colors = {
    //   dominant: {name: '', count: 0},
    //   palette: []
    // }

    let i = 0
    for (; i < data.length; i += 4) {
      rgb[0] = data[i]
      rgb[1] = data[i + 1]
      rgb[2] = data[i + 2]
      rgbString = rgb.join(',')

      // skip undefined data and transparent pixels
      if (rgb.indexOf(undefined) !== -1 || data[i + 3] === 0) {
        continue
      }

      // Ignore those colors in the exclude list.
      if (exclude.indexOf(makeRGB(rgbString)) === -1) {
        if (rgbString in colorCounts) {
          colorCounts[rgbString] = colorCounts[rgbString] + 1
        } else {
          colorCounts[rgbString] = 1
        }
      }
    }
    if (opts.success) {
      let palette = fitPalette(mapPalette(colorCounts), paletteSize + 1)
      opts.success({
        dominant: palette[0].name,
        secondary: palette[1].name,
        palette: palette.map(function (c) { return c.name }).slice(1)
      })
    }
  })
}

exports.install = function (Vue, options) {
  Vue.prototype.GetElementBottomMiddlePosition = GetElementBottomMiddlePosition
  Vue.prototype.ImgLoader = ImgLoader
  Vue.prototype.GetImgRGBaster = GetImgRGBaster
}

