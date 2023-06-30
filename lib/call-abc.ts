import { Abc } from "./abc";

export class CallAbc {
  abc: Abc;

  constructor() {
    this.abc = new Abc();
  }

  public async getAbc() {
    console.log("getAbc");
    this.abc.getAbc();
  }
}
