<template>
  <div>
      <form
  @submit="checkForm"
  action="/"
  method="post"
  novalidate="true"
>

  <p v-if="errors.length">
    <b>Please correct the following error(s):</b>
    <ul>
    </ul>
  </p>

  <p>
    Given a budget of 100 dollars, indicate how much
    you would spend on the following features for the
    next generation Star Destroyer. Your total must sum up to 100.
  </p>

  <p>
    <input
      v-model.number="weapons"
      type="number"
      name="weapons"
    > Weapons <br/>
    <input
      v-model.number="shields"
      type="number"
      name="shields"
    > Shields <br/>
    <input
      v-model.number="coffee"
      type="number"
      name="coffee"
    > Coffee <br/>
    <input
      v-model.number="ac"
      type="number"
      name="ac"
    > Air Conditioning <br/>
    <input
      v-model.number="mousedroids"
      type="number"
      name="mousedroids"
    > Mouse Droids <br/>
  </p>

  <p>
    Current Total: {{total}}
  </p>

  <p>
    <input
      type="submit"
      value="Submit"
    >
  </p>

</form>
</div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class FormTest extends Vue {
  errors: String[] = []
  weapons = 0
  shields = 0
  coffee = 0
  ac = 0
  mousedroids = 0


  get total() {
    // must parse because Vue turns empty value to string
    return Number(this.weapons) +
      Number(this.shields) +
      Number(this.coffee) +
      Number(this.ac + this.mousedroids);
  }

  checkForm(e: any) {
    this.errors = [];

    if (this.total != 100) {
      this.errors.push('Total must be 100!');
    }

    if (!this.errors.length) {
      return true;
    }

    e.preventDefault();
  }
}

</script>

<style scoped>
</style>