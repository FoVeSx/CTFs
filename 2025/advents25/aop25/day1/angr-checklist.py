import angr
import claripy
import sys

# Thanks Gemini! Nearly one shot this.

BINARY_PATH = './check-list' 
FLAG_LEN = 0x400 
WIN_ADDR  = 0x00aa46d4
LOSE_ADDR = 0x00aa476a

proj = angr.Project(BINARY_PATH, auto_load_libs=False)

stdin_bvs = claripy.BVS('stdin_flag', FLAG_LEN * 8)

state = proj.factory.entry_state(stdin=stdin_bvs)
state.options.add(angr.options.LAZY_SOLVES)

simgr = proj.factory.simulation_manager(state)
simgr.explore(find=WIN_ADDR, avoid=LOSE_ADDR)

if simgr.found:
    print("\n[!] Path found.")
    found_state = simgr.found[0]
    result = found_state.posix.dumps(sys.stdin.fileno())
    
    print(f"[!] Flag Length: {len(result)}")
    
    with open("flag.bin", "wb") as f:
        f.write(result)
        print("[!] Saved to flag.bin")
else:
    print("\n[!] No path found.")