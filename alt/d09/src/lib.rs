#[derive(Clone, Copy)]
struct Slot { l: usize, r: usize }

pub fn solve(players: usize, last_marble: usize) -> usize {
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

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn part1() {
        for (players, last_marble, expected) in [
            (9, 25, 32),
            (10, 1618, 8317),
            (13, 7999, 146373),
            (17, 1104, 2764),
            (21, 6111, 54718),
            (30, 5807, 37305),
            (416, 71617, 436720),
        ] {
            assert_eq!(expected, solve(players, last_marble));
        }
    }

    #[test]
    fn part2() {
        assert_eq!(3527845091, solve(416, 7161700));
    }
}
