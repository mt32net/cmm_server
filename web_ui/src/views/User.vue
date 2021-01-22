<template>
  <div class="container">
    <pre v-bind:class="[jsonHolderClass]" v-html="jsonPretty"></pre>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import axios from 'axios'

@Options({})
export default class User extends Vue {
  userString: any = {}
  jsonPretty = ""

  async mounted() {
    var result = await axios.get("/api/user/currentUser")
    this.userString = result.data
    this.outputPretty(JSON.stringify(this.userString, undefined, 4))
  }

  outputPretty(jsonstring: string) {
    jsonstring = jsonstring == '' ? this.userString : jsonstring;
    // prettify spacing
    var pretty = JSON.stringify(JSON.parse(jsonstring), undefined, 2);
    // syntaxhighlight the pretty print version
    this.jsonPretty = this.syntaxHighlight(pretty);
  }

  syntaxHighlight(json: string) {

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
.container {
  overflow: hidden;
  max-width: 80vw;
}

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