#[derive(Clone, Copy)]
struct Slot { l: usize, r: usize }

fn solve(players: usize, last_marble: usize) -> usize {
    let mut scores = vec![0; players];
    let mut slots = Vec::with_capacity(last_marble + 1);
    unsafe {
        slots.set_len(slots.capacity());
    }
    slots[0] = Slot { l: 0, r: 0 };
    let mut cur = 0;
    for new in 1..=last_marble {
        if new % 23 == 0 {
            let mut remove = cur;
            for _ in 0..7 {
                remove = slots[remove].l;
            }
            scores[new % players] += new + remove;
            let rem = slots[remove];
            slots[rem.l].r = rem.r;
            slots[rem.r].l = rem.l;
            cur = rem.r;
        } else {
            let l = slots[cur].r;
            let r = slots[l].r;
            slots[new] = Slot { l, r };
            slots[l].r = new;
            slots[r].l = new;
            cur = new;
        }
    }
    scores.into_iter().max().unwrap()
}

fn main() {
    for (players, last_marble, expected) in [
        //(9, 25, 32),
        //(10, 1618, 8317),
        //(13, 7999, 146373),
        //(17, 1104, 2764),
        //(21, 6111, 54718),
        //(30, 5807, 37305),
        //(416, 71617, 436720),
        (416, 7161700, 3527845091),
    ] {
        let res = solve(players, last_marble);
        let correct = expected == res;
        println!("{correct} {expected} {res}");
    }
}
