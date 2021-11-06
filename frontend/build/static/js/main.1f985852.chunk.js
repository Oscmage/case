(this.webpackJsonpfrontend=this.webpackJsonpfrontend||[]).push([[0],{51:function(e,t,n){},53:function(e,t,n){},54:function(e,t,n){},55:function(e,t,n){"use strict";n.r(t);var r=n(5),a=n.n(r),c=n(27),s=n.n(c),i=n(4),o=n.n(i),u=n(6),l=n(11),f=n(7),b=n(8),d=n(1),j=n(2),v=n(14),h=n(12),m=n(57),p=n(18),O=n.n(p),x=(n(51),n(0)),g={name:"",url:""},S=function(e){Object(v.a)(n,e);var t=Object(h.a)(n);function n(e){var r;return Object(d.a)(this,n),(r=t.call(this,e)).handleInputChangeUrl=function(e){var t=e.target.value;r.setState({url:t})},r.handleInputChangeName=function(e){var t=e.target.value;r.setState({name:t})},r.handleSubmit=function(){var e=Object(u.a)(o.a.mark((function e(t){var n;return o.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t.preventDefault(),e.next=3,r.props.create(r.state.name,r.state.url);case 3:null!==(n=e.sent)?alert("Received error: "+n):r.setState(g);case 5:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),r.state=g,r}return Object(j.a)(n,[{key:"render",value:function(){return Object(x.jsxs)("form",{className:"monitoring-form",onSubmit:this.handleSubmit,children:[Object(x.jsxs)("div",{className:"form-row",children:[Object(x.jsx)("label",{children:"Service Name:"}),Object(x.jsx)("input",{name:"serviceName",type:"text",value:this.state.name,onChange:this.handleInputChangeName})]}),Object(x.jsxs)("div",{className:"form-row",children:[Object(x.jsx)("label",{children:"Url:"}),Object(x.jsx)("input",{name:"urlToPoll",type:"text",value:this.state.url,onChange:this.handleInputChangeUrl})]}),Object(x.jsx)("input",{type:"submit",value:"Add monitoring"})]})}}]),n}(a.a.Component),y=n(13),N=(n(53),function(e){var t=Object.entries(e.services).map((function(e){var t=Object(y.a)(e,2),n=(t[0],t[1]);return Object(x.jsxs)("li",{children:[Object(x.jsxs)("div",{className:"service-text-info-wrapper",children:[Object(x.jsxs)("label",{children:["Name: ",n.name]}),Object(x.jsxs)("label",{children:["Url: ",n.url]}),Object(x.jsxs)("label",{children:["Created: ",n.creationTime]})]}),Object(x.jsxs)("div",{className:"service-status-wrapper tooltip",children:[Object(x.jsx)("span",{className:"dot "+n.status}),Object(x.jsx)("span",{className:"tooltiptext",children:n.status})]})]},n.url)}));return Object(x.jsx)("div",{children:Object(x.jsx)("ul",{className:"monitoring-list",children:t})})}),w=function(e){Object(v.a)(n,e);var t=Object(h.a)(n);function n(e){var r;return Object(d.a)(this,n),(r=t.call(this,e))._onSocketEvent=function(e){if(e.body){var t=JSON.parse(e.body),n=r.state.services,a={reference:t.reference,name:t.name,url:t.url,status:t.status,creationTime:t.creationTime},c=Object(b.a)(Object(b.a)({},n),{},Object(f.a)({},a.reference,a));r.setState({services:c})}},r.componentDidMount=function(){r.loadServices();var e=new m.a({brokerURL:"ws://localhost:8080/ws-monitoring",reconnectDelay:5e3,heartbeatIncoming:4e3,heartbeatOutgoing:4e3,onConnect:function(){console.log("Socket connection established!"),e.subscribe("/topic/monitoring",r._onSocketEvent)},onDisconnect:function(){console.log("Disconnected socket!")}});e.activate()},r.loadServices=function(){O.a.get("http://localhost:8080/list").then((function(e){var t,n=e.data,a={},c=Object(l.a)(n);try{for(c.s();!(t=c.n()).done;){var s=t.value;a[s.reference]={reference:s.reference,name:s.name,url:s.url,status:s.status,creationTime:s.creationTime}}}catch(i){c.e(i)}finally{c.f()}r.setState({services:a}),console.log("Initial load of services complete"),console.log(a)})).catch((function(e){console.error("Failed loading services on initilization")}))},r.createMonitoring=function(){var e=Object(u.a)(o.a.mark((function e(t,n){var a,c,s,i;return o.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,r._createMonitoring(t,n);case 2:if(a=e.sent,"undefined"!==typeof(c=a.error)){e.next=13;break}if("undefined"!==typeof a.service){e.next=7;break}return e.abrupt("return","Internal error");case 7:return s=r.state.services,i=Object(b.a)(Object(b.a)({},s),{},Object(f.a)({},a.service.reference,a.service)),r.setState((function(){return{services:i}})),e.abrupt("return",null);case 13:return e.abrupt("return",c);case 14:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),r._createMonitoring=function(){var e=Object(u.a)(o.a.mark((function e(t,n){var r;return o.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return r={name:t,url:n},e.next=3,O.a.post("http://localhost:8080/create",r).then((function(e){if(201===e.status){var t={reference:e.data.reference,name:e.data.name,url:e.data.url,status:e.data.status,creationTime:e.data.creationTime};return console.log(e.data),{service:t}}return{error:e.data}})).catch((function(e){return{error:e}}));case 3:return e.abrupt("return",e.sent);case 4:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),r.state={services:{}},r}return Object(j.a)(n,[{key:"render",value:function(){return Object(x.jsx)("div",{className:"App",children:Object(x.jsxs)("div",{className:"Form-Wrapper",children:[Object(x.jsx)(S,{create:this.createMonitoring}),Object(x.jsx)(N,{services:this.state.services})]})})}}]),n}(a.a.Component),k=(n(54),function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,58)).then((function(t){var n=t.getCLS,r=t.getFID,a=t.getFCP,c=t.getLCP,s=t.getTTFB;n(e),r(e),a(e),c(e),s(e)}))});s.a.render(Object(x.jsx)(a.a.StrictMode,{children:Object(x.jsx)(w,{})}),document.getElementById("root")),k()}},[[55,1,2]]]);
//# sourceMappingURL=main.1f985852.chunk.js.map