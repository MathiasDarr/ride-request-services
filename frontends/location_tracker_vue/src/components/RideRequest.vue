<template>
  <v-container>
    <v-form>
      <v-container fluid>
          <v-row>
          <v-col cols="12" sm="6">
            <v-text-field label="Destination" value="" v-model="destination"></v-text-field>
          </v-col>
          <v-col class="d-flex" cols="12" offset="2" sm="3">
            <v-select :items="riders" label="Number of riders" v-model="nriders"></v-select>
          </v-col>
        </v-row>
      </v-container>  
    </v-form>

  </v-container>
</template>


<script>
/* eslint-disable */


export default {
  data() {
    return {
      destination:null,
      nriders:1,
      riders: [1,2,3,4,5,6],
    };
  }

}
</script>

