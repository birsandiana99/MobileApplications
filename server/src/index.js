var koa = require('koa');
var app = module.exports = new koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');

app.use(bodyParser());

app.use(cors());

app.use(middleware);

function middleware(ctx, next) {
  const start = new Date();
  return next().then(() => {
    const ms = new Date() - start;
    console.log(`${start.toLocaleTimeString()} ${ctx.request.method} ${ctx.request.url} ${ctx.response.status} - ${ms}ms`);
  });
}


const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
};

const items = [];
const statuses = ['desired', 'needed', 'bought', 'canceled'];

for (let i = 0; i < 50; i++) {
  items.push({
    id: i + 1,
    name: "item" + i,
    quantity: getRandomInt(1, 10),
    price: getRandomInt(1, 100),
    status: statuses[getRandomInt(0, statuses.length)]
  });
}

const router = new Router();

router.get('/items', ctx => {
  ctx.response.body = items;
  ctx.response.status = 200;
});

const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.post('/item', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const name = headers.name;
  const quantity = headers.quantity;
  const price = headers.price;
  const status = headers.status;
  if (typeof name !== 'undefined'
    && typeof quantity !== 'undefined'
    && typeof price !== 'undefined'
    && typeof status !== 'undefined') {
    const index = items.findIndex(obj => obj.name == name);
    if (index !== -1) {
      console.log("Item already exists!");
      ctx.response.body = {text: 'Item already exists!'};
      ctx.response.status = 404;
    } else {
      let maxId = Math.max.apply(Math, items.map(function (obj) {
        return obj.id;
      })) + 1;
      let obj = {
        id: maxId,
        name,
        quantity,
        price,
        status
      };
      // console.log("created: " + JSON.stringify(name));
      items.push(obj);
      broadcast(obj);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid fields!");
    ctx.response.body = {text: 'Missing or invalid fields!'};
    ctx.response.status = 404;
  }
});

router.get('/item/:id', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  const id = headers.id;
  if (typeof id !== 'undefined') {
    const index = items.findIndex(obj => obj.id == id);
    if (index === -1) {
      console.log("Item not available!");
      ctx.response.body = {text: 'Item not available!'};
      ctx.response.status = 404;
    } else {
      let obj = items[index];
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid: id!");
    ctx.response.body = {text: 'Missing or invalid: id!'};
    ctx.response.status = 404;
  }
});

router.post('/buy', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  if (typeof id !== 'undefined') {
    const index = items.findIndex(obj => obj.id == id);
    if (index === -1) {
      console.log("Item not available!");
      ctx.response.body = {text: 'Item not available!'};
      ctx.response.status = 404;
    } else {
      let obj = items[index];
      obj.status = statuses[2];
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid id!");
    ctx.response.body = {text: 'Missing or invalid id!'};
    ctx.response.status = 404;
  }
});

router.get('/bought', ctx => {
  ctx.response.body = items.filter(obj => obj.status == statuses[2]);
  ctx.response.status = 200;
});

router.del('/item/:id', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  if (typeof id !== 'undefined') {
    const index = items.findIndex(obj => obj.id == id);
    if (index === -1) {
      console.log("No item with id: " + id);
      ctx.response.body = {text: 'Invalid item id'};
      ctx.response.status = 404;
    } else {
      let obj = items[index];
      items.splice(index, 1);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    ctx.response.body = {text: 'Id missing or invalid'};
    ctx.response.status = 404;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(2020);