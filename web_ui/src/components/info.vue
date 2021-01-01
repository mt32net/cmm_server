<template>
  <div>
    <h1>
      System running: <b v-bind:class="[runningClass]">{{ runningText }}</b>
    </h1>
    <pre ref="jsondata" v-bind:class="[jsonHolderClass]"></pre>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import axios from 'axios'

@Component
export default class info extends Vue {

  jsonHolder: any
  jsonString: String = ""
  jsonPretty: string = ""
  jsonHolderClass: String = ""

  runningClass: String = ""
  runningText: String = "checking..."

  mounted() {
    this.jsonHolder = this.$refs.jsondata
    axios.get("/api/info")
      .then((response) => {
        this.outputPretty(JSON.stringify(response.data, undefined, 4))
        this.runningText = "true"
        this.runningClass = "systemRunning"
        this.jsonHolderClass = ""
      })
      .catch((error) => {
        this.runningText = "false"
        this.runningClass = "error"
        this.jsonHolder.innerHTML = error + " \nUsed url: " + error.response.request.responseURL + "\nCheck Debug Console for more information"
        this.jsonHolderClass = "error"
        console.log(error.response.request.responseURL)
      })
  }

  outputPretty(jsonstring) {
    jsonstring = jsonstring == '' ? this.jsonString : jsonstring;
    // prettify spacing
    var pretty = JSON.stringify(JSON.parse(jsonstring), undefined, 2);
    // syntaxhighlight the pretty print version
    this.jsonPretty = this.syntaxHighlight(pretty);
    //output to a div
    // This could be a one liner with jQuery 
    // - but not making assumptions about jQuery or other library being available.
    var out = document.createElement("pre");
    out.innerHTML = this.jsonPretty;
    this.jsonHolder.appendChild(out);
  }

  syntaxHighlight(json) {

    if (typeof json != 'string') {
      json = JSON.stringify(json, undefined, 2);
    }

    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
      var cls = 'number';
      if (/^"/.test(match)) {
        if (/:$/.test(match)) {
          cls = 'key';
        } else {
          cls = 'string';
        }
      } else if (/true|false/.test(match)) {
        cls = 'boolean';
      } else if (/null/.test(match)) {
        cls = 'null';
      }
      return '<span class="' + cls + '">' + match + '</span>';
    });
  }
}
</script>

<style>
textarea {
  width: 90%;
  height: auto;
  font-family: "Lucida Console", Monaco, monospace;
  font-size: 0.8rem;
  line-height: 1.2;
  background-color: inherit;
  border: none;
  resize: none;
  color: inherit;
}

pre {
  font-family: "Lucida Console", Monaco, monospace;
  padding: 5px;
  margin: 2rem;
  text-align: left;
  width: 90%;
  height: auto;
  font-family: "Lucida Console", Monaco, monospace;
  font-size: 0.9rem;
  line-height: 1.4;
}

.string {
  color: #01bd01;
}

.number {
  color: #58e6ff;
}

.boolean {
  color: #b22222;
}

.null {
  color: #808080;
}

.key {
  color: #ffffff;
}

.systemRunning {
  color: green;
  font-weight: bold;
}

.error {
  color: red;
  font-weight: bold;
}
</style>